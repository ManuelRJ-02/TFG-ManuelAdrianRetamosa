import { Component, OnInit } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { CartShoppingService } from '../../services/cartShoppingService';
import { SessionService }      from '../../services/SessionService';
import { ProductVariantService } from '../../services/productVariantService';
import { forkJoin, map }      from 'rxjs';
import {Router} from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

declare const bootstrap: any;

interface AddedItem {
  title:  string;
  size:   string;
  amount: number;
  image:  string;
  price:  number;
}

@Component({
  selector: 'app-added-product',
  imports: [CommonModule, TranslatePipe],
  templateUrl: './added-product.component.html',
  styleUrl: './added-product.component.css'
})
export class AddedProductComponent implements OnInit {
  addedItems: AddedItem[] = [];
  constructor(private cartShopSvc: CartShoppingService, private sessionSvc: SessionService,
              private variantSvc: ProductVariantService, private router: Router) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    const user = this.sessionSvc.getUser();
    if (!user) return;

    this.cartShopSvc.getOpenCartByUser(user.id!).subscribe(cart => {
      const calls = cart.cartProducts.map(cp =>
        this.variantSvc.findById(cp.productVariantId).pipe(
          map(v => ({
            title: v.productName,
            size: v.productVariantSize,
            amount: cp.amount,
            image: v.productVariantImage,
            price: v.price
          }))
        )
      );
      forkJoin(calls).subscribe(items => this.addedItems = items);
    });
  }

  openPanel(): void {
    this.refresh();
    const el = document.getElementById('addedCanvas');
    if (el) new bootstrap.Offcanvas(el).show();
  }

  goToCart() {
    this.router.navigate(['/cart']);
  }
}
