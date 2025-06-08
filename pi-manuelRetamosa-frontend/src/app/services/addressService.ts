import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddressDTO } from '../models/addressDTO';
import {SessionService} from './SessionService';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private apiUrl = 'http://localhost:8080/addresses';

  constructor(private http: HttpClient, private session: SessionService) {}

  getAddressesByUser(userId: number): Observable<AddressDTO[]> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<AddressDTO[]>(`${this.apiUrl}/user/${userId}`, { headers });
  }

  addAddress(address: AddressDTO): Observable<AddressDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({ 'Authorization': `Basic ${basic}` });
    return this.http.post<AddressDTO>(`${this.apiUrl}/crear`, address, { headers });
  }

  deleteAddress(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({ 'Authorization': `Basic ${basic}` });
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, { headers });
  }

}
