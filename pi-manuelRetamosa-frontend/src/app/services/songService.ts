import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { SongDTO } from '../models/songDTO';
import { Observable } from 'rxjs';
import {ProductVariantDTO} from '../models/productVariantDTO';
import {SessionService} from './SessionService';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private apiUrl = 'http://localhost:8080/songs';

  constructor(private http: HttpClient, private session: SessionService) {}

  getAll(): Observable<SongDTO[]> {
    return this.http.get<SongDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<SongDTO> {
    return this.http.get<SongDTO>(`${this.apiUrl}/${id}`);
  }

  create(song: SongDTO): Observable<SongDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<SongDTO>(`${this.apiUrl}/crear`, song, { headers });
  }

  update(id: number, song: SongDTO): Observable<SongDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.put<SongDTO>(`${this.apiUrl}/editar/${id}`, song, { headers });
  }

  delete(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, { headers });
  }

  uploadCover(file: File): Observable<string> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/uploadCover`, formData, { headers, responseType: 'text' });
  }
}
