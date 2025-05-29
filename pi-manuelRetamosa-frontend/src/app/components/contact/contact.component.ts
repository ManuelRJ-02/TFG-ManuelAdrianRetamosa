import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import { ContactService} from '../../services/contactService';
import { ContactDTO} from '../../models/contactDTO';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-contact',
  imports: [CommonModule, FormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contact: ContactDTO = { name: '', email: '', message: '' };
  errorMsg = '';

  constructor(private contactService: ContactService, private router: Router) {}

  onSubmit(form: NgForm) {
    if (form.invalid) return;

    this.contactService.send(this.contact).subscribe({
      next: () => {
        this.router.navigate(['']);
      },
      error: () => {
        this.errorMsg = 'Error enviando el mensaje.';
      }
    });
  }
}
