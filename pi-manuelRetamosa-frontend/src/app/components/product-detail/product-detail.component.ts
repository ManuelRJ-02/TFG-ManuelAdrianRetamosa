import {Component, OnInit, ViewChild} from '@angular/core';
import { CommonModule }            from '@angular/common';
import { ActivatedRoute, Router }  from '@angular/router';
import { ProductVariantService }   from '../../services/productVariantService';
import { CartShoppingService }     from '../../services/cartShoppingService';
import { CartProductService }      from '../../services/cartProductService';
import { SessionService }          from '../../services/SessionService';
import { ProductVariantDTO }       from '../../models/productVariantDTO';
import { CartShoppingDTO }         from '../../models/cartShoppingDTO';
import { CartProductDTO }          from '../../models/cartProductDTO';
import { AddedProductComponent }         from '../added-product/added-product.component';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, AddedProductComponent, TranslatePipe],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  @ViewChild(AddedProductComponent) addedCmp!: AddedProductComponent;

  variants: ProductVariantDTO[] = [];
  colors: { color: string; image: string }[] = [];
  selectedColor!: string;
  selectedVariant!: ProductVariantDTO;
  quantity = 1;
  private userId!: number;
  private cartId!: number;

  constructor(private route: ActivatedRoute, private router: Router, private variantSvc: ProductVariantService,
              private cartShopSvc: CartShoppingService, private cartProdSvc: CartProductService, private sessionSvc: SessionService) {}

  ngOnInit(): void {
    const user = this.sessionSvc.getUser();
    if (!user) {
      this.router.navigate(['/merchandising'], { queryParams: { openLogin: true } });
      return;
    }
    this.userId = user.id!;
    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.variantSvc.findByProduct(productId).subscribe({
      next: vs => {
        this.variants = vs;
        this.colors = Array.from(
          new Map(vs.map(v => [v.color, { color: v.color, image: v.productVariantImage }])).values()
        );
        this.selectedColor = this.colors[0].color;
        this.selectVariant(this.variants.find(v => v.color === this.selectedColor)!);

        this.cartShopSvc.getOpenCartByUser(this.userId).subscribe({
          next: (cart: CartShoppingDTO) => this.cartId = cart.id,
          error: err => console.error('No pude obtener el carrito abierto', err)
        });
      },
      error: err => console.error('Error cargando variantes para productId=' + productId, err)
    });
  }

  selectColor(color: string) {
    this.selectedColor = color;
    const first = this.variants.find(v => v.color === color)!;
    this.selectVariant(first);
  }

  selectVariant(v: ProductVariantDTO) {
    this.selectedVariant = v;
    this.quantity = 1;
  }

  sizesForColor(): ProductVariantDTO[] {
    return this.variants.filter(v => v.color === this.selectedColor);
  }

  increaseQuantity() {
    if (this.quantity < this.selectedVariant.stock) {
      this.quantity++;
    }
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    const dto: CartProductDTO = {
      id:               0,
      amount:           this.quantity,
      unitPrice:        this.selectedVariant.price,
      productVariantId: this.selectedVariant.id!,
      cartShoppingId:   this.cartId
    };

    this.cartProdSvc.create(dto).subscribe({
      next: () => {
        this.variantSvc.findById(this.selectedVariant.id!).subscribe({
          next: fresh => {
            this.selectedVariant = fresh;
            const i = this.variants.findIndex(v => v.id === fresh.id);
            if (i >= 0) this.variants[i] = fresh;
            if (this.selectedVariant.stock === 0) {
              this.quantity = 0;
            } else if (this.quantity > this.selectedVariant.stock) {
              this.quantity = this.selectedVariant.stock;
            }
          },
          error: err => console.error('No pude refrescar la variante tras añadir al carrito', err)
        });
        this.addedCmp.refresh();
        setTimeout(() => this.addedCmp.openPanel(), 0);
      },
      error: err => console.error('Error al añadir al carrito:', err)
    });
  }
}
