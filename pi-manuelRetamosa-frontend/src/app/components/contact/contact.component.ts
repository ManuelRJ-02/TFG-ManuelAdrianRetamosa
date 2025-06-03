import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import { ContactService} from '../../services/contactService';
import { ContactDTO} from '../../models/contactDTO';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-contact',
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contact: ContactDTO = {
    name: '',
    email: '',
    message: ''
  };
  errorMessages: string[] = [];

  constructor(
    private contactService: ContactService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
    if (form.invalid) return;

    this.errorMessages = [];

    this.contactService.send(this.contact).subscribe({
      next: () => {
        this.router.navigate(['mail-sent']);
      },
      error: (err) => {
        if (err.status === 400 && Array.isArray(err.error.errors)) {
          this.errorMessages = err.error.errors;
        } else {
          this.errorMessages = [
            err.error.message || 'Error enviando el mensaje.'
          ];
        }
      }
    });
  }
}
