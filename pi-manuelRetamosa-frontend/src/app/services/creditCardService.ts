import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreditCardDTO } from '../models/creditCardDTO';

@Injectable({
  providedIn: 'root',
})
export class CreditCardService {
  private apiUrl = 'http://localhost:8080/creditCards';

  constructor(private http: HttpClient) {}

  getByUser(userId: number): Observable<CreditCardDTO[]> {
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}/user/${userId}`);
  }

  getAll(): Observable<CreditCardDTO[]> {
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}`);
  }

  create(card: CreditCardDTO): Observable<CreditCardDTO> {
    return this.http.post<CreditCardDTO>(`${this.apiUrl}/crear`, card);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`);
  }
}
