import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { AuthService} from '../../services/authService';
import { UserDTO} from '../../models/userDTO';
import {CommonModule} from '@angular/common';
import { SessionService} from '../../services/SessionService';
import { Router} from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-register',
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user: UserDTO = {
    userName: '',
    surname: '',
    email: '',
    userPassword: '',
    phoneNumber: '',
    avatar: 'assets/header/user-icon.png'
  };

  confirmPassword: string = '';
  errorMessages: string[] = [];

  constructor(private authService: AuthService, private sessionService: SessionService, private router: Router) {}

  onSubmit(form: NgForm): void {
    this.errorMessages = [];
    this.user.email = this.user.email.toLowerCase();

    if (this.user.userPassword !== this.confirmPassword) {
      this.errorMessages = ['Las contraseñas no coinciden'];
      return;
    }

    this.authService.register(this.user).subscribe({
      next: () => {
        this.authService.login({
          email: this.user.email,
          userPassword: this.user.userPassword
        }).subscribe({
          next: (user) => {
            this.sessionService.setUser(user, this.user.userPassword);
            this.errorMessages = [];
            form.reset();

            const modalElement = document.getElementById('register-modal');
            if (modalElement) {
              const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
              modal.hide();
            }

            this.router.navigate(['/profile']);
          },
          error: (err) => {
            this.errorMessages = [err.error.message || 'Error al iniciar sesión después del registro'];
          }
        });
      },
      error: (err) => {
        if (err.error.errors) {
          this.errorMessages = err.error.errors;
        } else {
          this.errorMessages = [err.error.message || 'Error desconocido'];
        }
      }
    });
  }

  togglePassword(inputId: string, iconId: string): void {
    const input = document.getElementById(inputId) as HTMLInputElement;
    const icon = document.getElementById(iconId);
    if (input && icon) {
      const isText = input.type === 'text';
      input.type = isText ? 'password' : 'text';
      icon.classList.toggle('fa-eye', !isText);
      icon.classList.toggle('fa-eye-slash', isText);
    }
  }
}
