import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-biography',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './biography.component.html',
  styleUrl: './biography.component.css'
})
export class BiographyComponent {
  showMore = false;
  toggleContenido() {
    this.showMore = !this.showMore;
  }
}

