import { Component } from '@angular/core';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-accessibility-statement',
  imports: [
    TranslatePipe
  ],
  templateUrl: './accessibility-statement.component.html',
  styleUrl: './accessibility-statement.component.css'
})
export class AccessibilityStatementComponent {

}
