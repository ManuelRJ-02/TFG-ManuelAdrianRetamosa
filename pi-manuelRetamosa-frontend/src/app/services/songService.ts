import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SongDTO } from '../models/songDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private apiUrl = 'http://localhost:8080/songs';

  constructor(private http: HttpClient) {}

  getAll(): Observable<SongDTO[]> {
    return this.http.get<SongDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<SongDTO> {
    return this.http.get<SongDTO>(`${this.apiUrl}/${id}`);
  }

  create(song: SongDTO): Observable<SongDTO> {
    return this.http.post<SongDTO>(`${this.apiUrl}/crear`, song);
  }

  update(id: number, song: SongDTO): Observable<SongDTO> {
    return this.http.put<SongDTO>(`${this.apiUrl}/editar/${id}`, song);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`);
  }

  uploadCover(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/uploadCover`, formData, { responseType: 'text' });
  }
}
