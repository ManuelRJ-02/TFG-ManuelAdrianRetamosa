import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { CartShoppingService } from '../../services/cartShoppingService';
import { CartProductService }  from '../../services/cartProductService';
import { SessionService }      from '../../services/SessionService';
import { CartProductDTO  }      from '../../models/cartProductDTO';
import { ProductVariantDTO }    from '../../models/productVariantDTO';
import { ProductVariantService }from '../../services/productVariantService';
import { OrderService } from '../../services/orderService';

interface CartViewItem {
  cartProductIds:    number[];
  productVariantId:  number;
  title:             string;
  image:             string;
  size:              string;
  price:             number;
  quantity:          number;
  stock:             number;
}

@Component({
  selector: 'app-cart',
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartViewItem[] = [];
  totalItems = 0;
  totalPrice = 0;
  cartId!: number;

  constructor(private cartShoppingSvc: CartShoppingService, private cartProductSvc: CartProductService, private variantSvc: ProductVariantService,
    private sessionService: SessionService, private router: Router, private orderService: OrderService) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (!user) {
      this.router.navigate(['/']);
      return;
    }
    this.refreshCartContents();
  }

  private refreshCartContents() {
    const user = this.sessionService.getUser();
    if (!user) return;
    this.cartShoppingSvc.getOpenCartByUser(user.id!).subscribe(cart => {
      this.cartId = cart.id;
      this.buildViewItems(cart.cartProducts);
      this.totalItems = cart.cartProducts.reduce((sum, cp) => sum + cp.amount, 0);
      this.totalPrice = cart.total;
    });
  }

  private buildViewItems(cartProducts: CartProductDTO[]) {
    const requests = cartProducts.map(cp =>
      this.variantSvc.findById(cp.productVariantId).pipe(
        map((variant: ProductVariantDTO) => ({
          cartProductId:     cp.id!,
          productVariantId:  cp.productVariantId,
          title:             variant.productName,
          image:             variant.productVariantImage,
          size:              variant.productVariantSize,
          price:             cp.unitPrice,
          quantity:          cp.amount,
          stock:             variant.stock
        }))
      )
    );

    forkJoin(requests).subscribe(items => {
      const map = new Map<number, {
          cartProductIds: number[];
          quantity:       number;
          title:          string;
          image:          string;
          size:           string;
          price:          number;
          stock:          number;
      }>();

      items.forEach(item => {
        const key = item.productVariantId;
        if (!map.has(key)) {
          map.set(key, {
            cartProductIds: [item.cartProductId],
            quantity:       item.quantity,
            title:          item.title,
            image:          item.image,
            size:           item.size,
            price:          item.price,
            stock:          item.stock
          });
        } else {
          const existing = map.get(key)!;
          existing.cartProductIds.push(item.cartProductId);
          existing.quantity += item.quantity;
        }
      });

      this.cartItems = Array.from(map.entries()).map(
        ([variantId, info]) =>
          ({
            cartProductIds:   info.cartProductIds,
            productVariantId: variantId,
            title:            info.title,
            image:            info.image,
            size:             info.size,
            price:            info.price,
            quantity:         info.quantity,
            stock:            info.stock
          } as CartViewItem)
      );
    });
  }

  increaseQuantity(index: number) {
    const item = this.cartItems[index];
    const nuevaCantidad = item.quantity + 1;

    this.cartProductSvc
      .update(
        item.cartProductIds[0],
        {
          id:                item.cartProductIds[0],
          amount:            nuevaCantidad,
          unitPrice:         item.price,
          productVariantId:  item.productVariantId,
          cartShoppingId:    this.cartId
        }
      )
      .subscribe({
        next: () => {
          this.refreshCartContents();
        },
        error: (err) => console.error('Error al actualizar cantidad:', err)
      });
  }

  decreaseQuantity(index: number) {
    const item = this.cartItems[index];
    if (item.quantity > 1) {
      const nuevaCantidad = item.quantity - 1;
      this.cartProductSvc
        .update(
          item.cartProductIds[0],
          {
            id:                item.cartProductIds[0],
            amount:            nuevaCantidad,
            unitPrice:         item.price,
            productVariantId:  item.productVariantId,
            cartShoppingId:    this.cartId
          }
        )
        .subscribe({
          next: () => {
            this.refreshCartContents();
          },
          error: (err) => console.error('Error al restar unidad:', err)
        });
    } else {
      const calls = item.cartProductIds.map(id => this.cartProductSvc.delete(id));
      forkJoin(calls).subscribe({
        next: () => {
          this.cartItems.splice(index, 1);
          this.totalItems = this.cartItems.reduce((sum, i) => sum + i.quantity, 0);
          this.totalPrice = this.cartItems.reduce(
            (sum, i) => sum + i.quantity * i.price,
            0
          );
          if (this.cartItems.length) {
            this.refreshCartContents();
          }
        },
        error: (err) => console.error('Error borrando la variante completa:', err)
      });
    }
  }

  deleteItem(index: number) {
    const item = this.cartItems[index];
    const calls = item.cartProductIds.map(id => this.cartProductSvc.delete(id));

    forkJoin(calls).subscribe({
      next: () => {
        this.cartItems.splice(index, 1);
        this.totalItems = this.cartItems.reduce((sum, i) => sum + i.quantity, 0);
        this.totalPrice = this.cartItems.reduce(
          (sum, i) => sum + i.quantity * i.price,
          0
        );
        if (this.cartItems.length) {
          this.refreshCartContents();
        }
      },
      error: (err) => console.error('Error borrando todas las filas de esa variante:', err)
    });
  }

  clearCart() {
    this.cartProductSvc.deleteByCart(this.cartId).subscribe({
      next: () => {
        this.cartItems = [];
        this.totalItems = 0;
        this.totalPrice = 0;
      },
      error: (err) => console.error('Error vaciando carrito', err)
    });
  }

  goToConfirmPurchase() {
    this.orderService.upsertFromCart(this.cartId).subscribe({
      next: () => {
        this.router.navigate(['/confirm-purchase']);
      },
      error: (err) => console.error('Error al preparar la orden:', err)
    });
  }
}
