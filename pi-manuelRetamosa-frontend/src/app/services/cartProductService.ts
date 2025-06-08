import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CartProductDTO } from '../models/cartProductDTO';
import { Observable } from 'rxjs';
import {SessionService} from './SessionService';

@Injectable({ providedIn: 'root' })
export class CartProductService {
  private apiUrl = 'http://localhost:8080/cartProducts';

  constructor(private http: HttpClient, private session: SessionService) {}

  delete(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, { headers });
  }

  deleteByCart(cartShoppingId: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/byCart/${cartShoppingId}`, { headers });
  }

  update(id: number, dto: CartProductDTO): Observable<CartProductDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.put<CartProductDTO>(`${this.apiUrl}/editar/${id}`, dto, { headers });
  }

  create(dto: CartProductDTO): Observable<CartProductDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<CartProductDTO>(`${this.apiUrl}/crear`, dto, { headers });
  }
}
