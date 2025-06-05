import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, map } from 'rxjs';
import { OrderService} from '../../services/orderService';
import { ProductVariantService }   from '../../services/productVariantService';
import { SessionService} from '../../services/SessionService';
import { OrderDTO} from '../../models/orderDTO';
import { ProductVariantDTO }from '../../models/productVariantDTO';
import {AddressComponent} from '../address/address.component';
import {PaymentMethodComponent} from '../payment-method/payment-method.component';
import { AddressService} from '../../services/addressService';
import { AddressDTO} from '../../models/addressDTO';
import {CreditCardDTO} from '../../models/creditCardDTO';
import {CreditCardService} from '../../services/creditCardService';
import { Router } from '@angular/router';

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
  subtotal = 0;
  totalPrice = '0.00';

  addresses: AddressDTO[] = [];
  selectedAddressId: number | null = null;

  creditCards: CreditCardDTO[] = [];
  selectedCardId: number | null = null;

  constructor(private orderSvc: OrderService, private variantSvc: ProductVariantService, public sessionService: SessionService,
              private addressService: AddressService, private creditCardService: CreditCardService, private router: Router) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (!user) return;
    this.orderSvc.getLatestByUser(user.id!).subscribe(order => {
      this.loadDetailOrders(order);
    });
    this.loadAddresses();
    this.loadCreditCards();
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

  canFinish(): boolean {
    return (this.selectedAddressId !== null && this.selectedCardId !== null && this.termsAccepted === true);
  }

  finishPurchase() {
    if (!this.canFinish()) {
      return;
    }
    console.log('COMPRA confirmada:', {
      items: this.cartItems,
      addressId: this.selectedAddressId,
      cardId: this.selectedCardId,
      total: this.totalPrice
    });
    this.router.navigate(['purchase-made']);
  }

  private loadAddresses() {
    const user = this.sessionService.getUser();
    if (!user) return;

    this.addressService.getAddressesByUser(user.id!).subscribe({
      next: (arr) => {
        this.addresses = arr;
        if (this.addresses.length > 0 && this.selectedAddressId === null) {
          this.selectedAddressId = this.addresses[0].id!;
        }
      },
      error: (err) => {
        console.error('Error cargando direcciones:', err);
      }
    });
  }

  onAddressSelectionChange(addrId: number) {
    this.selectedAddressId = addrId;
  }

  onAddressAdded() {
    this.loadAddresses();
  }

  private loadCreditCards() {
    const user = this.sessionService.getUser();
    if (!user) return;
    this.creditCardService.getByUser(user.id!).subscribe({
      next: (cardsDelUsuario) => {
        this.creditCards = cardsDelUsuario;
        if (this.creditCards.length > 0 && this.selectedCardId === null) {
          this.selectedCardId = this.creditCards[0].id!;
        }
      },
      error: (err) => {
        console.error('Error cargando tarjetas del usuario:', err);
      }
    });
  }

  onCreditCardSelectionChange(cardId: number) {
    this.selectedCardId = cardId;
  }

  onCreditCardAdded() {
    this.loadCreditCards();
  }
}
