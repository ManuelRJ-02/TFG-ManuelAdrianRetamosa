import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CartShoppingDTO } from '../models/cartShoppingDTO';
import { Observable } from 'rxjs';
import {AddressDTO} from '../models/addressDTO';
import {SessionService} from './SessionService';

@Injectable({ providedIn: 'root' })
export class CartShoppingService {
  private apiUrl = 'http://localhost:8080/cartShoppings';

  constructor(private http: HttpClient, private session: SessionService) {}

  getOpenCartByUser(userId: number): Observable<CartShoppingDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<CartShoppingDTO>(`${this.apiUrl}/user/${userId}` , { headers });
  }
}
