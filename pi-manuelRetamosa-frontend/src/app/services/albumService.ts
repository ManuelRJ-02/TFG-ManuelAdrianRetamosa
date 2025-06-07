import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AlbumDTO } from '../models/albumDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private apiUrl = 'http://localhost:8080/albums';

  constructor(private http: HttpClient) {}

  getAll(): Observable<AlbumDTO[]> {
    return this.http.get<AlbumDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<AlbumDTO> {
    return this.http.get<AlbumDTO>(`${this.apiUrl}/${id}`);
  }

  create(album: AlbumDTO): Observable<AlbumDTO> {
    return this.http.post<AlbumDTO>(`${this.apiUrl}/crear`, album);
  }

  update(id: number, album: AlbumDTO): Observable<AlbumDTO> {
    return this.http.put<AlbumDTO>(`${this.apiUrl}/editar/${id}`, album);
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
