import { Component } from '@angular/core';
import { AuthService } from '../../services/authService';
import { LoginDTO } from '../../models/loginDTO';
import {FormsModule } from '@angular/forms';
import {CommonModule } from '@angular/common';
import { NgForm } from '@angular/forms';
import { SessionService } from '../../services/SessionService';
import { Router} from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'

})
export class LoginComponent {
  credentials: LoginDTO = {
    email: '',
    userPassword: ''
  };

  showPassword: boolean = false;
  errorMessages: string[] = [];

  constructor(private authService: AuthService, private sessionService: SessionService, private router: Router) {}

  onSubmit(form: NgForm): void {
    this.errorMessages = [];
    this.authService.login(this.credentials).subscribe({
      next: (user) => {
        this.errorMessages = [];
        this.sessionService.setUser(user);
        form.reset();
        const modalElement = document.getElementById('login-modal');
        if (modalElement) {
          const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
          modal.hide();
        }
        this.router.navigate(['/profile']);
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

  togglePassword(): void {
    const input = document.getElementById('password') as HTMLInputElement;
    const icon = document.querySelector('.input-group-text i');

    if (input && icon) {
      this.showPassword = !this.showPassword;
      input.type = this.showPassword ? 'text' : 'password';
      icon.classList.toggle('fa-eye-slash', !this.showPassword);
      icon.classList.toggle('fa-eye', this.showPassword);
    }
  }
}
