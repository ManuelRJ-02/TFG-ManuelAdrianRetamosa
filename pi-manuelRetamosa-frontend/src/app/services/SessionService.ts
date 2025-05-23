import { Injectable } from '@angular/core';
import { UserDTO } from '../models/userDTO';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private userKey = 'loggedUser';

  private userSubject = new BehaviorSubject<UserDTO | null>(this.getUserFromStorage());
  user$ = this.userSubject.asObservable();

  private getUserFromStorage(): UserDTO | null {
    const userJson = localStorage.getItem(this.userKey);
    return userJson ? JSON.parse(userJson) : null;
  }

  setUser(user: UserDTO): void {
    localStorage.setItem(this.userKey, JSON.stringify(user));
    this.userSubject.next(user);
  }

  getUser(): UserDTO | null {
    return this.userSubject.value;
  }

  clearUser(): void {
    localStorage.removeItem(this.userKey);
    this.userSubject.next(null);
  }

  isLogged(): boolean {
    return !!this.userSubject.value;
  }

  logout(): void {
    this.clearUser();
  }
}
