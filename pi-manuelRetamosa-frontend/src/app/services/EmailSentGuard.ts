import { Injectable } from '@angular/core';
import {CanActivate, Router, UrlTree} from '@angular/router';
import { ContactService} from './contactService';

@Injectable({ providedIn: 'root' })
export class EmailSentGuard implements CanActivate {
  constructor(private contactSvc: ContactService, private router: Router) {}

  canActivate(): boolean | UrlTree {
    if (this.contactSvc.hasSent()) {
      return true;
    }
    return this.router.createUrlTree(['']);
  }
}
