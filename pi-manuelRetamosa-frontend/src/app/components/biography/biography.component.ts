import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-biography',
  imports: [CommonModule],
  templateUrl: './biography.component.html',
  styleUrl: './biography.component.css'
})
export class BiographyComponent {
  showMore = false;
  toggleContenido() {
    this.showMore = !this.showMore;
  }
}

