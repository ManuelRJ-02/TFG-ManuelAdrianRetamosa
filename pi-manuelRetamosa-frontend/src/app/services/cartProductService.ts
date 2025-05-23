import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CartProductDTO } from '../models/cartProductDTO';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CartProductService {
  private apiUrl = 'http://localhost:8080/cartProducts';

  constructor(private http: HttpClient) {}

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`);
  }

  deleteByCart(cartShoppingId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/byCart/${cartShoppingId}`);
  }

  update(id: number, dto: CartProductDTO): Observable<CartProductDTO> {
    return this.http.put<CartProductDTO>(`${this.apiUrl}/editar/${id}`, dto);
  }

  create(dto: CartProductDTO): Observable<CartProductDTO> {
    return this.http.post<CartProductDTO>(`${this.apiUrl}/crear`, dto);
  }
}
