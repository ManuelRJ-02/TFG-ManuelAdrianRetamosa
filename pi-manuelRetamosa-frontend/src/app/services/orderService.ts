import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderDTO } from '../models/orderDTO';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private apiUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}

  getById(orderId: number): Observable<OrderDTO> {
    return this.http.get<OrderDTO>(`${this.apiUrl}/${orderId}`);
  }

  getLatestByUser(userId: number): Observable<OrderDTO> {
    return this.http.get<OrderDTO>(`${this.apiUrl}/user/${userId}/latest`);
  }

  upsertFromCart(cartId: number): Observable<OrderDTO> {
    return this.http.post<OrderDTO>(`${this.apiUrl}/create/${cartId}`, {});
  }

  finalizeFromCart(cartId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/finalize/${cartId}`, {});
  }
}
