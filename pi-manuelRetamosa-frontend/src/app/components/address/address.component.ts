import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-address',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent { }
