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

  /**
   * Devuelve únicamente las tarjetas asociadas al usuario indicado
   * (mapea a GET /creditCards/user/{userId})
   */
  getByUser(userId: number): Observable<CreditCardDTO[]> {
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}/user/${userId}`);
  }

  /** Devuelve todas las tarjetas (si en algún caso las necesitaras) */
  getAll(): Observable<CreditCardDTO[]> {
    return this.http.get<CreditCardDTO[]>(`${this.apiUrl}`);
  }

  /** Crea una nueva tarjeta enviando el DTO (que incluye userId) */
  create(card: CreditCardDTO): Observable<CreditCardDTO> {
    return this.http.post<CreditCardDTO>(`${this.apiUrl}/crear`, card);
  }

  /** Elimina la tarjeta con id dado */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`);
  }
}
