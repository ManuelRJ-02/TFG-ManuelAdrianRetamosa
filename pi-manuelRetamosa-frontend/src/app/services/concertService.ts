import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConcertDTO} from '../models/concertDTO';
import {SessionService} from './SessionService';

@Injectable({
  providedIn: 'root',
})
export class ConcertService {
  private apiUrl = 'http://localhost:8080/concerts';

  constructor(private http: HttpClient, private session: SessionService) {}

  getAll(): Observable<ConcertDTO[]> {
    return this.http.get<ConcertDTO[]>(`${this.apiUrl}`);
  }

  getById(id: number): Observable<ConcertDTO> {
    return this.http.get<ConcertDTO>(`${this.apiUrl}/${id}`);
  }

  create(concert: ConcertDTO): Observable<ConcertDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<ConcertDTO>(`${this.apiUrl}`, concert, { headers });
  }

  update(id: number, concert: ConcertDTO): Observable<ConcertDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.put<ConcertDTO>(`${this.apiUrl}/${id}`, concert, { headers });
  }

  delete(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }
}
