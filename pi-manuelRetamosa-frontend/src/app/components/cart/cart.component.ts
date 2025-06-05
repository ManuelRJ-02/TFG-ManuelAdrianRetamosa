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
  cartProductId:     number;
  productVariantId:  number;
  title:             string;
  image:             string;
  size:              string;
  price:             number;
  quantity:          number;
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

  constructor(
    private cartShoppingSvc: CartShoppingService,
    private cartProductSvc:  CartProductService,
    private variantSvc:      ProductVariantService,
    private sessionService:  SessionService,
    private router:          Router,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (!user) {
      this.router.navigate(['/']);
      return;
    }
    this.cartShoppingSvc.getOpenCartByUser(user.id!).subscribe(cart => {
      this.cartId = cart.id;  // <-- guardamos el cartId
      this.buildViewItems(cart.cartProducts);
      this.totalPrice = cart.total;
      this.totalItems = cart.cartProducts.reduce((sum, cp) => sum + cp.amount, 0);
    });
  }

  private buildViewItems(cartProducts: CartProductDTO[]) {
    const requests = cartProducts.map(cp =>
      this.variantSvc.findById(cp.productVariantId).pipe(
        map((variant: ProductVariantDTO) => ({
          cartProductId:     cp.id,
          productVariantId:  cp.productVariantId,
          title:             variant.productName,
          image:             variant.productVariantImage,
          size:              variant.productVariantSize,
          price:             cp.unitPrice,
          quantity:          cp.amount
        } as CartViewItem))
      )
    );

    forkJoin<CartViewItem[]>(requests).subscribe(items => {
      this.cartItems = this.mergeDuplicates(items);
    });
  }

  private mergeDuplicates(items: CartViewItem[]): CartViewItem[] {
    const map = new Map<number, CartViewItem>();
    items.forEach(item => {
      const key = item.productVariantId;
      if (!map.has(key)) {
        map.set(key, { ...item });
      } else {
        map.get(key)!.quantity += item.quantity;
      }
    });
    return Array.from(map.values());
  }

  increaseQuantity(index: number) {
    const item = this.cartItems[index];
    const nuevaCantidad = item.quantity + 1;

    this.cartProductSvc.update(
      item.cartProductId,
      {
        id: item.cartProductId,
        amount: nuevaCantidad,
        unitPrice: item.price,
        productVariantId: item.productVariantId,
        cartShoppingId: this.cartId
      }
    ).subscribe({
      next: updatedCartProduct => {
        this.cartItems[index].quantity = updatedCartProduct.amount;
        this.refreshCartTotal();
      },
      error: err => console.error('Error al actualizar cantidad:', err)
    });
  }

  decreaseQuantity(index: number) {
    const item = this.cartItems[index];
    if (item.quantity <= 1) return;

    const nuevaCantidad = item.quantity - 1;
    this.cartProductSvc.update(
      item.cartProductId,
      {
        id: item.cartProductId,
        amount: nuevaCantidad,
        unitPrice: item.price,
        productVariantId: item.productVariantId,
        cartShoppingId: this.cartId
      }
    ).subscribe({
      next: updatedCartProduct => {
        this.cartItems[index].quantity = updatedCartProduct.amount;
        this.refreshCartTotal();
      },
      error: err => console.error('Error al actualizar cantidad:', err)
    });
  }

  private refreshCartTotal() {
    this.cartShoppingSvc.getOpenCartByUser(this.sessionService.getUser()!.id!).subscribe(cart => {
      this.totalItems = cart.cartProducts.reduce((sum, cp) => sum + cp.amount, 0);
      this.totalPrice = cart.total;
    });
  }

  deleteItem(index: number) {
    const cpId = this.cartItems[index].cartProductId;
    this.cartProductSvc.delete(cpId).subscribe(() => {
      const item = this.cartItems[index];
      this.totalItems -= item.quantity;
      this.totalPrice -= (item.price * item.quantity);

      this.cartItems.splice(index, 1);
    });
  }

  clearCart() {
    this.cartProductSvc.deleteByCart(this.cartId).subscribe({
      next: () => {
        this.cartItems = [];
        this.totalItems = 0;
        this.totalPrice = 0;
      },
      error: err => console.error('Error vaciando carrito', err)
    });
  }

  goToConfirmPurchase() {

    this.orderService.upsertFromCart(this.cartId).subscribe({
      next: orderDto => {
        this.router.navigate(['/confirm-purchase']);
      },
      error: err => {
        console.error('Error al preparar la orden:', err);
      }
    });

  }
}


