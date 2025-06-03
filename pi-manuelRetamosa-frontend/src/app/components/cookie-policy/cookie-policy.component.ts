import { Component } from '@angular/core';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-cookie-policy',
  imports: [
    TranslatePipe
  ],
  templateUrl: './cookie-policy.component.html',
  styleUrl: './cookie-policy.component.css'
})
export class CookiePolicyComponent {

}
