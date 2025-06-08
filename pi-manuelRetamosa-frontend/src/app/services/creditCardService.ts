import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreditCardDTO } from '../models/creditCardDTO';
import {CartProductDTO} from '../models/cartProductDTO';
import {SessionService} from './SessionService';

@Injectable({
  providedIn: 'root',
})
export class CreditCardService {
  private apiUrl = 'http://localhost:8080/creditCards';

  constructor(private http: HttpClient, private session: SessionService) {}

  getByUser(userId: number): Observable<CreditCardDTO[]> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}/user/${userId}`, { headers });
  }

  getAll(): Observable<CreditCardDTO[]> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}`, { headers });
  }

  create(card: CreditCardDTO): Observable<CreditCardDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`, 'Content-Type':  'application/json'});
    return this.http.post<CreditCardDTO>(`${this.apiUrl}/crear`, card, { headers });
  }

  delete(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, { headers });
  }
}
