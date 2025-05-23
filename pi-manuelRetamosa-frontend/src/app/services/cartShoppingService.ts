import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CartShoppingDTO } from '../models/cartShoppingDTO';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CartShoppingService {
  private apiUrl = 'http://localhost:8080/cartShoppings';

  constructor(private http: HttpClient) {}

  getOpenCartByUser(userId: number): Observable<CartShoppingDTO> {
    return this.http.get<CartShoppingDTO>(`${this.apiUrl}/user/${userId}`);
  }
}
