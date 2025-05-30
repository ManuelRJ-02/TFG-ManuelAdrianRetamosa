import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContactDTO} from '../models/contactDTO';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ContactService {
  private apiUrl = 'http://localhost:8080/contact';
  private _hasSent = false;

  constructor(private http: HttpClient) {}

  send(contact: ContactDTO): Observable<{ message: string }> {
    const url = `${this.apiUrl}/sendMail`;
    return this.http
      .post<{ message: string }>(url, contact)
      .pipe(
        tap(() => this._hasSent = true)
      );
  }

  hasSent(): boolean {
    return this._hasSent;
  }

}
