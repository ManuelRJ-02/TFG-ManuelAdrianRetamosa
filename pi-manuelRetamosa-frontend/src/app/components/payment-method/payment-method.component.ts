import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment-method',
  imports: [ CommonModule, FormsModule ],
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.css']
})
export class PaymentMethodComponent {
  cardholder = '';
  cardNumber = '';
  expiryMonth = '';
  expiryYear = '';
  securityCode = '';
}
