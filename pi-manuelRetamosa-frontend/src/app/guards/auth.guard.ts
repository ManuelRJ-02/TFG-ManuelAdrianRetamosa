import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService} from '../services/SessionService';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private sessionService: SessionService, private router: Router) {}

  canActivate(): boolean {
    const user = this.sessionService.getUser();
    if (user) {
      return true;
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }
}
