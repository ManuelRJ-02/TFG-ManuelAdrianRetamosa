import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { ProductVariantDTO } from '../models/productVariantDTO';
import { Observable } from 'rxjs';
import {AddressDTO} from '../models/addressDTO';
import {SessionService} from './SessionService';

@Injectable({ providedIn: 'root' })
export class ProductVariantService {
  private apiUrl = 'http://localhost:8080/productVariants';

  constructor(private http: HttpClient, private session: SessionService) {}

  getAll(): Observable<ProductVariantDTO[]> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<ProductVariantDTO[]>(this.apiUrl, { headers });
  }

  findByProduct(productId: number): Observable<ProductVariantDTO[]> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<ProductVariantDTO[]>(`${this.apiUrl}/byProduct/${productId}`, { headers });
  }

  findById(id: number): Observable<ProductVariantDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.get<ProductVariantDTO>(`${this.apiUrl}/${id}`, { headers });
  }

  create(dto: ProductVariantDTO): Observable<ProductVariantDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.post<ProductVariantDTO>(`${this.apiUrl}/crear`, dto, { headers });
  }

  update(id: number, dto: ProductVariantDTO): Observable<ProductVariantDTO> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.put<ProductVariantDTO>(`${this.apiUrl}/editar/${id}`, dto, { headers });
  }

  delete(id: number): Observable<void> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    return this.http.delete<void>(`${this.apiUrl}/borrar/${id}`, { headers });
  }

  uploadImage(file: File): Observable<string> {
    const user = this.session.getUser()!;
    const pwd  = this.session.getPassword()!;
    const basic = btoa(`${user.email}:${pwd}`);
    const headers = new HttpHeaders({'Authorization': `Basic ${basic}`});
    const form = new FormData();
    form.append('file', file);
    return this.http.post(`${this.apiUrl}/uploadImage`, form, { headers, responseType: 'text' });
  }
}
