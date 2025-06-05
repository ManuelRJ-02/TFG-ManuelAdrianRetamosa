import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddressDTO } from '../models/addressDTO';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private apiUrl = 'http://localhost:8080/addresses';

  constructor(private http: HttpClient) {}

  getAddressesByUser(userId: number): Observable<AddressDTO[]> {
    return this.http.get<AddressDTO[]>(`${this.apiUrl}/user/${userId}`);
  }

  addAddress(address: AddressDTO): Observable<AddressDTO> {
    return this.http.post<AddressDTO>(`${this.apiUrl}/crear`, address);
  }

}
