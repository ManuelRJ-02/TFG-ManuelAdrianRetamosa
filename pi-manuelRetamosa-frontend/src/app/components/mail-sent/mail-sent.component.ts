import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-mail-sent',
  imports: [],
  templateUrl: './mail-sent.component.html',
  styleUrl: './mail-sent.component.css'
})
export class MailSentComponent {
  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/']);
  }
}
