import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-purchase-made',
  imports: [
    TranslatePipe
  ],
  templateUrl: './purchase-made.component.html',
  styleUrl: './purchase-made.component.css'
})
export class PurchaseMadeComponent {

  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/']);
  }
}
