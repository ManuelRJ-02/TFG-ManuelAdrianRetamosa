import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductVariantDTO } from '../models/productVariantDTO';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductVariantService {
  private apiUrl = 'http://localhost:8080/productVariants';

  constructor(private http: HttpClient) {}

  findByProduct(productId: number): Observable<ProductVariantDTO[]> {
    return this.http.get<ProductVariantDTO[]>(`${this.apiUrl}/byProduct/${productId}`);
  }

  findById(id: number): Observable<ProductVariantDTO> {
    return this.http.get<ProductVariantDTO>(`${this.apiUrl}/${id}`);
  }
}
