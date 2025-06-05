import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserDTO } from '../../models/userDTO';
import { SessionService} from '../../services/SessionService';
import { AuthService} from '../../services/authService';
import { AddressService} from '../../services/addressService';
import { AddressDTO } from '../../models/addressDTO';
import { AddressComponent } from '../address/address.component';
import {ProfileDTO} from '../../models/ProfileDTO';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, FormsModule, AddressComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  user: UserDTO | null = null;

  /** Lista de direcciones del usuario */
  addresses: AddressDTO[] = [];
  /** ID de la dirección seleccionada */
  selectedAddressId: number | null = null;

  // Campos editables:
  saveError = '';
  saveSuccess = '';
  isSaving = false;

  // Para la subida de avatar (sólo si usabas esa parte):
  isUploading = false;
  uploadError = '';
  uploadSuccess = '';

  constructor(
    private sessionService: SessionService,
    private authService: AuthService,
    private addressService: AddressService
  ) {}

  ngOnInit(): void {
    this.user = this.sessionService.getUser();
    if (this.user) {
      this.loadAddresses();
    }
  }

  /** Carga todas las direcciones del usuario usando AddressService */
  loadAddresses() {
    if (!this.user) return;
    this.addressService.getAddressesByUser(this.user.id!).subscribe({
      next: (arr) => {
        this.addresses = arr;
        if (this.addresses.length > 0 && this.selectedAddressId === null) {
          this.selectedAddressId = this.addresses[0].id!;
        }
      },
      error: (err) => {
        console.error('Error cargando direcciones:', err);
      },
    });
  }

  /** Cuando <app-address> emite “addressAdded” */
  onAddressAdded() {
    this.loadAddresses();
  }

  /**
   * Guarda nombre, apellidos, teléfono y avatar
   * Construye un ProfileDTO (no un UserDTO) y llama a updateProfile(...)
   */
  onSaveProfile() {
    if (!this.user) return;
    this.saveError = '';
    this.saveSuccess = '';
    this.isSaving = true;

    // Construimos un ProfileDTO
    const profilePayload: ProfileDTO = {
      id: this.user.id!,
      userName: this.user.userName!,
      surname: this.user.surname!,
      email: this.user.email!,
      avatar: this.user.avatar,          // puede venir undefined o la URL
      phoneNumber: this.user.phoneNumber, // puede venir undefined o el teléfono
    };

    this.authService.updateProfile(profilePayload).subscribe({
      next: (updated) => {
        // Actualizamos la sesión con lo que devuelve el backend
        this.sessionService.setUser(updated, this.sessionService.getPassword()!);
        this.user = updated;
        this.saveSuccess = 'Datos actualizados correctamente.';
      },
      error: (err) => {
        if (err.error?.errors) {
          // si el backend devolvió un array de errores de validación
          this.saveError = (err.error.errors as string[]).join(', ');
        } else {
          this.saveError = err.error?.message || 'Error al guardar los datos';
        }
      },
      complete: () => {
        this.isSaving = false;
      },
    });
  }

  /** Seleccionar otra dirección */
  onAddressSelectionChange(addrId: number) {
    this.selectedAddressId = addrId;
    // (Si quisieras, podrías enviar un PUT extra para marcar esta dirección como “preferida”)
  }

  /** Métodos de avatar, igual que antes… */
  triggerFileSelect(fileInput: HTMLInputElement) {
    fileInput.click();
  }
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files?.length && this.user) {
      const file = input.files[0];
      this.uploadError = '';
      this.uploadSuccess = '';
      this.uploadAvatarFile(file);
      input.value = '';
    }
  }
  uploadAvatarFile(file: File) {
    if (!file || !this.user) {
      this.uploadError = 'Selecciona primero un archivo.';
      return;
    }
    this.isUploading = true;
    this.authService.updateAvatarFile(this.user.id!, file).subscribe({
      next: (updatedUser) => {
        this.sessionService.setUser(updatedUser, this.sessionService.getPassword()!);
        this.user = updatedUser;
        this.uploadSuccess = 'Avatar actualizado correctamente.';
        this.isUploading = false;
      },
      error: (err) => {
        this.uploadError = err.error?.message || 'Error al subir el avatar';
        this.isUploading = false;
      },
    });
  }
}
