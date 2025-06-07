import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductVariantDTO } from '../models/productVariantDTO';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductVariantService {
  private apiUrl = 'http://localhost:8080/productVariants';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProductVariantDTO[]> {
    return this.http.get<ProductVariantDTO[]>(this.apiUrl);
  }

  findByProduct(productId: number): Observable<ProductVariantDTO[]> {
    return this.http.get<ProductVariantDTO[]>(`${this.apiUrl}/byProduct/${productId}`);
  }

  findById(id: number): Observable<ProductVariantDTO> {
    return this.http.get<ProductVariantDTO>(`${this.apiUrl}/${id}`);
  }

  create(dto: ProductVariantDTO): Observable<ProductVariantDTO> {
    return this.http.post<ProductVariantDTO>(`${this.apiUrl}/crear`, dto);
  }

  update(id: number, dto: ProductVariantDTO): Observable<ProductVariantDTO> {
    return this.http.put<ProductVariantDTO>(`${this.apiUrl}/editar/${id}`, dto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`);
  }

  uploadImage(file: File): Observable<string> {
    const form = new FormData();
    form.append('file', file);
    return this.http.post(`${this.apiUrl}/uploadImage`, form, { responseType: 'text' });
  }
}
