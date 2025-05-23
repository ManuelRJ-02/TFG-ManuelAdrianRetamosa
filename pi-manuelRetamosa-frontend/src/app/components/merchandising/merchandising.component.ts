import { Component, OnInit } from '@angular/core';
import { CommonModule }    from '@angular/common';
import { Router }         from '@angular/router';
import { ProductService } from '../../services/productService';
import { ProductDTO }     from '../../models/productDTO';

@Component({
  selector: 'app-merchandising',
  imports: [ CommonModule ],
  templateUrl: './merchandising.component.html',
  styleUrls: ['./merchandising.component.css']
})
export class MerchandisingComponent implements OnInit {
  tShirts: ProductDTO[] = [];
  hoodies: ProductDTO[] = [];

  constructor(
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: products => {
        this.tShirts = products.filter(p => p.category.toLowerCase() === 'tshirt');
        this.hoodies = products.filter(p => p.category.toLowerCase() === 'hoodie');
      },
      error: err => console.error('Error cargando productos', err)
    });
  }

  viewProductDetail(id: number) {
    this.router.navigate(['/product', id]);
  }
}
