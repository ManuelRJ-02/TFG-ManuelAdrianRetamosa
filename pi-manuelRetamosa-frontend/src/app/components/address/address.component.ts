import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslatePipe } from '@ngx-translate/core';
import { AddressService} from '../../services/addressService';
import { SessionService } from '../../services/SessionService';
import { AddressDTO } from '../../models/addressDTO';

declare var bootstrap: any;

@Component({
  selector: 'app-address',
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent {
  @Output() addressAdded = new EventEmitter<void>();

  country:     string = '';
  province:    string = '';
  city:        string = '';
  postalCode:  string = '';
  street:      string = '';
  blockNumber: string = '';
  ladder:      string = '';
  door:        string = '';
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private addressService: AddressService, private sessionService: SessionService) {}

  onSubmitAddAddress() {
    this.errorMessage = '';

    const user = this.sessionService.getUser();
    if (!user) {
      this.errorMessage = 'Usuario no autenticado.';
      return;
    }

    if (!this.country || !this.province || !this.city || !this.postalCode || !this.street || !this.blockNumber || !this.ladder || !this.door) {
      this.errorMessage = 'Debes completar todos los campos.';
      return;
    }

    const newAddress: AddressDTO = {
      street: this.street,
      city: this.city,
      province: this.province,
      postalCode: this.postalCode,
      country: this.country,
      blockNumber: this.blockNumber,
      ladder: this.ladder,
      door: this.door,
      userId: user.id!
    };

    this.isSubmitting = true;
    this.addressService.addAddress(newAddress).subscribe({
      next: (created) => {
        this.addressAdded.emit();
        this.closeModal();
        this.resetForm();
        this.isSubmitting = false;
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Error al guardar la dirección';
        this.isSubmitting = false;
      },
    });
  }

  private resetForm() {
      this.country = '';
      this.province = '';
      this.city = '';
      this.postalCode = '';
      this.street = '';
      this.blockNumber = '';
      this.ladder = '';
      this.door = '';
      this.errorMessage = '';
    }

  private closeModal() {
      const modalEl = document.getElementById('address-modal');
      if (modalEl) {
        const modal = bootstrap.Modal.getInstance(modalEl);
        if (modal) {
          modal.hide();
        }
      }
    }
}
