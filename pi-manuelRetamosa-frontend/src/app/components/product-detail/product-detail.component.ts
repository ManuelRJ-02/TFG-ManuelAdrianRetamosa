import { Component, OnInit }      from '@angular/core';
import { CommonModule }            from '@angular/common';
import { ActivatedRoute, Router }  from '@angular/router';
import { ProductVariantService }   from '../../services/productVariantService';
import { CartShoppingService }     from '../../services/cartShoppingService';
import { CartProductService }      from '../../services/cartProductService';
import { SessionService }          from '../../services/SessionService';
import { ProductVariantDTO }       from '../../models/productVariantDTO';
import { CartShoppingDTO }         from '../../models/cartShoppingDTO';
import { CartProductDTO }          from '../../models/cartProductDTO';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  variants: ProductVariantDTO[] = [];
  colors:   { color: string; image: string }[] = [];
  selectedColor!:   string;
  selectedVariant!: ProductVariantDTO;
  quantity = 1;

  private userId!: number;
  private cartId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private variantSvc: ProductVariantService,
    private cartShopSvc: CartShoppingService,
    private cartProdSvc: CartProductService,
    private sessionSvc: SessionService
  ) {}

  ngOnInit(): void {
    // 1) Comprobamos si hay usuario logueado
    const user = this.sessionSvc.getUser();
    if (!user) {
      this.router.navigate(['/merchandising'], { queryParams: { openLogin: true } });
      return;
    }
    this.userId = user.id!;
    const productId = Number(this.route.snapshot.paramMap.get('id'));
    this.variantSvc.findByProduct(productId).subscribe(vs => {
      this.variants = vs;
      this.colors = Array.from(
        new Map(
          vs.map(v => [v.color, { color: v.color, image: v.productVariantImage }])
        ).values()
      );
      this.selectColor(this.colors[0].color);

      this.cartShopSvc.getOpenCartByUser(this.userId).subscribe((cart: CartShoppingDTO) => {
        this.cartId = cart.id;
      });
    });
  }

  selectColor(color: string) {
    this.selectedColor = color;
    const firstVariant = this.variants.find(v => v.color === color)!;
    this.selectVariant(firstVariant);
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
      next: () => alert('Producto añadido al carrito!'),
      error: err => console.error('Error al añadir:', err)
    });
  }
}
