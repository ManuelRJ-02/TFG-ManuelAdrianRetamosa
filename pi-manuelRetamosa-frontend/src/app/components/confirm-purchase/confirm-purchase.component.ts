import { Component, OnInit }      from '@angular/core';
import { CommonModule }            from '@angular/common';
import { FormsModule }             from '@angular/forms';
import { forkJoin, map }           from 'rxjs';
import { OrderService} from '../../services/orderService';
import { ProductVariantService }   from '../../services/productVariantService';
import { SessionService }          from '../../services/SessionService';
import { OrderDTO }         from '../../models/orderDTO';
import { ProductVariantDTO }from '../../models/productVariantDTO';
import {AddressComponent} from '../address/address.component';
import {PaymentMethodComponent} from '../payment-method/payment-method.component';

interface CartViewItem {
  title:    string;
  image:    string;
  size:     string;
  price:    number;
  quantity: number;
}

@Component({
  selector: 'app-confirm-purchase',
  imports: [CommonModule, FormsModule, AddressComponent, PaymentMethodComponent],
  templateUrl: './confirm-purchase.component.html',
  styleUrls: ['./confirm-purchase.component.css']
})
export class ConfirmPurchaseComponent implements OnInit {
  cartItems: CartViewItem[] = [];
  shippingCost = 3;
  termsAccepted = false;
  selectedPaymentMethod = '';
  subtotal = 0;
  totalPrice = '0.00';

  constructor(
    private orderSvc: OrderService,
    private variantSvc: ProductVariantService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (!user) return;
    this.orderSvc.getLatestByUser(user.id!).subscribe(order => {
      this.loadDetailOrders(order);
    });
  }

  private loadDetailOrders(order: OrderDTO) {
    const calls = order.detailOrders.map(d =>
      this.variantSvc.findById(d.productVariantId!).pipe(
        map((v: ProductVariantDTO) => ({
          title: v.productName,
          image: v.productVariantImage,
          size: v.productVariantSize,
          price: v.price,
          quantity: d.amount!
        } as CartViewItem))
      )
    );

    forkJoin<CartViewItem[]>(calls).subscribe(items => {
      this.cartItems = items;
      this.subtotal  = items.reduce((s, i) => s + i.price * i.quantity, 0);
      this.totalPrice = (this.subtotal + this.shippingCost).toFixed(2);
    });
  }

  finishPurchase() {
    if (!this.termsAccepted || !this.selectedPaymentMethod) {
      return alert('Debes aceptar los términos y elegir método de pago');
    }
    console.log('COMPRA:', {
      items: this.cartItems,
      payment: this.selectedPaymentMethod,
      total: this.totalPrice
    });
  }
}
