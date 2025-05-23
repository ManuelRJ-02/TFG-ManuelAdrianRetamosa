import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDTO} from '../models/userDTO';
import { LoginDTO} from '../models/loginDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {}

  register(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/register`, user);
  }

  login(credentials: LoginDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>('http://localhost:8080/users/login', credentials);
  }

}
