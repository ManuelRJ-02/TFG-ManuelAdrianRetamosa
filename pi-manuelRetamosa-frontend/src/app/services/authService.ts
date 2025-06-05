import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { UserDTO} from '../models/userDTO';
import { LoginDTO} from '../models/loginDTO';
import { Observable } from 'rxjs';
import { SessionService} from './SessionService';
import {ProfileDTO} from '../models/ProfileDTO';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient, private sessionService: SessionService) {}

  register(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/register`, user);
  }

  login(credentials: LoginDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>('http://localhost:8080/users/login', credentials);
  }

  updateUser(user: UserDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(
      `${this.apiUrl}/editar/${user.id}`,
      user
    );
  }

  updateAvatarFile(userId: number, file: File): Observable<UserDTO> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.put<UserDTO>(`${this.apiUrl}/avatar/${userId}`, formData);
  }

  updateProfile(profile: ProfileDTO): Observable<UserDTO> {
    const url = `${this.apiUrl}/editar/${profile.id}`;
    const currentUser = this.sessionService.getUser()!;
    const currentPwd = this.sessionService.getPassword()!;
    const credentials = btoa(`${currentUser.email}:${currentPwd}`);
    const headers = new HttpHeaders({
      Authorization: `Basic ${credentials}`,
      'Content-Type': 'application/json'
    });
    return this.http.put<UserDTO>(url, profile, { headers });
  }
}
