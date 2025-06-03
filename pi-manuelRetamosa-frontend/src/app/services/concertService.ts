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
}
