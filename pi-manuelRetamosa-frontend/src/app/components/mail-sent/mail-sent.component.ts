import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-mail-sent',
  imports: [
    TranslatePipe
  ],
  templateUrl: './mail-sent.component.html',
  styleUrl: './mail-sent.component.css'
})
export class MailSentComponent {
  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/']);
  }
}
