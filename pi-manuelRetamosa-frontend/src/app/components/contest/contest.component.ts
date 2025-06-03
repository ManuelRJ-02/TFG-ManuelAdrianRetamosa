import { Component } from '@angular/core';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-contest',
  imports: [
    TranslatePipe
  ],
  templateUrl: './contest.component.html',
  styleUrl: './contest.component.css'
})
export class ContestComponent {

}
