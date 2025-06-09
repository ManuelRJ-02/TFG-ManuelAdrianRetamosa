import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../services/SessionService';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {

  constructor(private session: SessionService, private router: Router) {}

  canActivate(): boolean {
    const user = this.session.getUser();
    const isAdmin = user?.roles?.some((r: any) => r.roleName === 'ROLE_ADMIN');
    if (isAdmin) {
      return true;
    }
    this.router.navigate(['/']);
    return false;
  }
}
