import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductDTO} from '../models/productDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.apiUrl}/${id}`);
  }

  create(prod: ProductDTO): Observable<ProductDTO> {
    return this.http.post<ProductDTO>(`${this.apiUrl}/crear`, prod);
  }

  update(id: number, prod: ProductDTO): Observable<ProductDTO> {
    return this.http.put<ProductDTO>(
      `${this.apiUrl}/editar/${id}`, prod
    );
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
