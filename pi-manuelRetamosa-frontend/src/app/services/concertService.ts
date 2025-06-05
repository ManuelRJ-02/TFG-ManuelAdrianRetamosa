import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConcertDTO} from '../models/concertDTO';

@Injectable({
  providedIn: 'root',
})
export class ConcertService {
  private apiUrl = 'http://localhost:8080/concerts';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ConcertDTO[]> {
    return this.http.get<ConcertDTO[]>(`${this.apiUrl}`);
  }

  getById(id: number): Observable<ConcertDTO> {
    return this.http.get<ConcertDTO>(`${this.apiUrl}/${id}`);
  }

  create(concert: ConcertDTO): Observable<ConcertDTO> {
    return this.http.post<ConcertDTO>(`${this.apiUrl}`, concert);
  }

  update(id: number, concert: ConcertDTO): Observable<ConcertDTO> {
    return this.http.put<ConcertDTO>(`${this.apiUrl}/${id}`, concert);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
