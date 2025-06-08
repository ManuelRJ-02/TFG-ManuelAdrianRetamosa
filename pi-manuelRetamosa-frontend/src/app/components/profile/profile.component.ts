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
  addresses: AddressDTO[] = [];
  selectedAddressId: number | null = null;

  saveErrors: string[] = [];
  isSaving = false;

  isUploading = false;
  uploadError = '';
  uploadSuccess = '';

  constructor(private sessionService: SessionService, private authService: AuthService, private addressService: AddressService) {}

  ngOnInit(): void {
    this.user = this.sessionService.getUser();
    if (this.user) {
      this.loadAddresses();
    }
  }

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

  onAddressAdded() {
    this.loadAddresses();
  }

  onSaveProfile() {
    if (!this.user) return;
    this.saveErrors = [];
    this.isSaving = true;

    const profilePayload: ProfileDTO = {
      id: this.user.id!,
      userName: this.user.userName!,
      surname: this.user.surname!,
      email: this.user.email!,
      avatar: this.user.avatar,
      phoneNumber: this.user.phoneNumber,
    };

    this.authService.updateProfile(profilePayload).subscribe({
      next: (updated) => {
        this.sessionService.setUser(updated, this.sessionService.getPassword()!);
        this.user = updated;
      },
      error: (err) => {
        if (err.error?.errors) {
          this.saveErrors = err.error.errors as string[];
        } else {
          this.saveErrors = err.error?.message || 'Error al guardar los datos';
        }
      },
      complete: () => {
        this.isSaving = false;
      },
    });
  }

  onAddressSelectionChange(addrId: number) {
    this.selectedAddressId = addrId;
  }

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
