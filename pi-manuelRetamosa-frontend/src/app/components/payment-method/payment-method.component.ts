import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { CreditCardService } from '../../services/creditCardService';
import { SessionService } from '../../services/SessionService';
import { CreditCardDTO } from '../../models/creditCardDTO';
import {TranslatePipe} from '@ngx-translate/core';

declare var bootstrap: any;

@Component({
  selector: 'app-payment-method',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.css']
})
export class PaymentMethodComponent {
  cardholder = '';
  cardNumber = '';
  expiryMonth: string | null = null;
  expiryYear: string | null = null;
  securityCode = '';

  @Output() creditCardAdded = new EventEmitter<void>();

  isSaving = false;
  errorMsgs: string[] = [];

  months: string[] = ['01','02','03','04','05','06','07','08','09','10','11','12'];
  years: string[] = ['24','25','26','27','28', '29','30','31','32','33','34'];

  constructor(private creditCardSvc: CreditCardService, private sessionService: SessionService) {}

  onExpiryChange(): void {
    if (!this.errorMsgs || !this.expiryMonth || !this.expiryYear) {
      return;
    }

    const today = new Date();
    const currentYear2 = String(today.getFullYear()).slice(-2);
    const currentMonth = (today.getMonth() + 1).toString().padStart(2, '0');
    const selYearNum = parseInt(this.expiryYear, 10);
    const selMonthNum = parseInt(this.expiryMonth, 10);
    const curYearNum = parseInt(currentYear2, 10);
    const curMonthNum = parseInt(currentMonth, 10);

    if (selYearNum > curYearNum || (selYearNum === curYearNum && selMonthNum >= curMonthNum)) {
      this.errorMsgs = [];
    }
  }

  onAddCard(): void {
    this.errorMsgs = [];
    this.isSaving = true;

    const user = this.sessionService.getUser();
    if (!user || !user.id) {
      this.errorMsgs = ['Usuario no autenticado.'];
      this.isSaving = false;
      return;
    }

    const dto: CreditCardDTO = {
      holder: this.cardholder.trim(),
      cardNumber: this.cardNumber.replace(/\s+/g, ''),
      expirationMonth: this.expiryMonth!,
      expirationYear: this.expiryYear!,
      securityCode: this.securityCode.trim(),
      userId: user.id!
    };

    this.creditCardSvc.create(dto).subscribe({
      next: () => {
        this.cardholder = '';
        this.cardNumber = '';
        this.expiryMonth = null;
        this.expiryYear = null;
        this.securityCode = '';

        this.creditCardAdded.emit();

        const modalEl = document.getElementById('payment-modal');
        if (modalEl) {
          const modalInstance =
            bootstrap.Modal.getInstance(modalEl) ||
            new bootstrap.Modal(modalEl);
          modalInstance.hide();
        }
      },
      error: err => {
        const payload = err.error;
        if (Array.isArray(payload?.errors)) {
          this.errorMsgs = payload.errors;
        } else if (payload?.message) {
          this.errorMsgs = [ payload.message ];
        } else {
          this.errorMsgs = ['Error desconocido al añadir la tarjeta'];
        }
        this.isSaving = false;
      }
    });
  }
}
