import { Component, OnInit }          from '@angular/core';
import { CommonModule }               from '@angular/common';
import { FormsModule }                from '@angular/forms';
import { Router, ActivatedRoute }     from '@angular/router';
import { NgxSliderModule, Options }   from '@angular-slider/ngx-slider';
import { ProductService }             from '../../services/productService';
import { ProductDTO }                 from '../../models/productDTO';

declare var bootstrap: any;

@Component({
  selector: 'app-merchandising',
  imports: [CommonModule, FormsModule, NgxSliderModule],
  templateUrl: './merchandising.component.html',
  styleUrls: ['./merchandising.component.css']
})
export class MerchandisingComponent implements OnInit {
  private allTShirts: ProductDTO[] = [];
  private allHoodies: ProductDTO[] = [];

  tShirts: ProductDTO[] = [];
  hoodies: ProductDTO[] = [];
  filterType: 'all' | 'tshirt' | 'hoodie' = 'all';
  minPrice: number = 0;
  maxPrice: number = 1250;

  sliderOptions: Options = {
    floor: 0,
    ceil: 1250,
    step: 1,
    translate: (value: number): string => {
      return `${Math.round(value)} €`;
    }
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const openLogin = params.get('openLogin');
      if (openLogin === 'true') {
        setTimeout(() => {
          const modalEl = document.getElementById('login-modal');
          if (modalEl) {
            const modal = bootstrap.Modal.getInstance(modalEl)
              || new bootstrap.Modal(modalEl);
            modal.show();
          }
        }, 50);
      }
    });

    this.productService.getAll().subscribe({
      next: products => {
        this.allTShirts = products.filter(p => p.category.toLowerCase() === 'tshirt');
        this.allHoodies = products.filter(p => p.category.toLowerCase() === 'hoodie');
        const precios: number[] = [
          ...this.allTShirts.map(p => p.priceBase),
          ...this.allHoodies.map(p => p.priceBase)
        ];
        if (precios.length > 0) {
          const maxPriceAbsolute = Math.max(...precios);
          const ceilRounded = Math.ceil(maxPriceAbsolute / 50) * 50;
          this.sliderOptions = {
            ...this.sliderOptions,
            floor: 0,
            ceil: ceilRounded
          };
          this.minPrice = 0;
          this.maxPrice = ceilRounded;
        }

        this.applyFilters();
      },
      error: err => console.error('Error cargando productos', err)
    });
  }

  applyFilters(): void {
    const filteredT = this.allTShirts.filter(p =>
      p.priceBase >= this.minPrice && p.priceBase <= this.maxPrice
    );
    const filteredH = this.allHoodies.filter(p =>
      p.priceBase >= this.minPrice && p.priceBase <= this.maxPrice
    );

    switch (this.filterType) {
      case 'tshirt':
        this.tShirts = filteredT;
        this.hoodies = [];
        break;
      case 'hoodie':
        this.tShirts = [];
        this.hoodies = filteredH;
        break;
      default: // 'all'
        this.tShirts = filteredT;
        this.hoodies = filteredH;
        break;
    }
  }

  viewProductDetail(id?: number): void {
    this.router.navigate(['/product', id]);
  }
}
