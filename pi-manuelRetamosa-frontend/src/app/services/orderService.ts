import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { OrderDTO } from '../models/orderDTO';
import { Observable } from 'rxjs';
import {SessionService} from './SessionService';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private apiUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient, private session: SessionService) {}

  getById(orderId: number): Observable<OrderDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<OrderDTO>(`${this.apiUrl}/${orderId}`, { headers });
  }

  getLatestByUser(userId: number): Observable<OrderDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<OrderDTO>(`${this.apiUrl}/user/${userId}/latest`, { headers });
  }

  upsertFromCart(cartId: number): Observable<OrderDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<OrderDTO>(`${this.apiUrl}/create/${cartId}`, {}, { headers });
  }

  finalizeFromCart(cartId: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<void>(`${this.apiUrl}/finalize/${cartId}`, {}, { headers });
  }
}
