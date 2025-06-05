import { Injectable } from '@angular/core';
import { UserDTO } from '../models/userDTO';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private userKey = 'loggedUser';
  private pwdKey = 'loggedPwd';

  private userSubject = new BehaviorSubject<UserDTO | null>(this.getUserFromStorage());
  user$ = this.userSubject.asObservable();

  private getUserFromStorage(): UserDTO | null {
    const userJson = localStorage.getItem(this.userKey);
    return userJson ? JSON.parse(userJson) : null;
  }

  private getPasswordFromStorage(): string | null {
    return localStorage.getItem(this.pwdKey);
  }

  setUser(user: UserDTO, password: string): void {
    localStorage.setItem(this.userKey, JSON.stringify(user));
    localStorage.setItem(this.pwdKey, password);
    this.userSubject.next(user);
  }

  getUser(): UserDTO | null {
    return this.userSubject.value;
  }

  getPassword(): string | null {
    return this.getPasswordFromStorage();
  }

  clearUser(): void {
    localStorage.removeItem(this.userKey);
    localStorage.removeItem(this.pwdKey);
    this.userSubject.next(null);
  }

  isLogged(): boolean {
    return !!this.userSubject.value;
  }

  logout(): void {
    this.clearUser();
  }
}
