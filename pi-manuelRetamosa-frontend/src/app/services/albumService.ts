import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { AlbumDTO } from '../models/albumDTO';
import { Observable } from 'rxjs';
import {SessionService} from './SessionService';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private apiUrl = 'http://localhost:8080/albums';

  constructor(private http: HttpClient, private session: SessionService) {}

  getAll(): Observable<AlbumDTO[]> {
    return this.http.get<AlbumDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<AlbumDTO> {
    return this.http.get<AlbumDTO>(`${this.apiUrl}/${id}`);
  }

  create(album: AlbumDTO): Observable<AlbumDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<AlbumDTO>(`${this.apiUrl}/crear`, album, { headers });
  }

  update(id: number, album: AlbumDTO): Observable<AlbumDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.put<AlbumDTO>(`${this.apiUrl}/editar/${id}`, album, { headers });
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
