import { Component } from '@angular/core';
import {RouterLink, RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";
import { SessionService} from '../../services/SessionService';
import { UserDTO } from '../../models/userDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  currentLanguaje: 'es' | 'en' | 'it' = 'es';
  openMenu: boolean = false;
  loggedUser: UserDTO | null = null;

  constructor(private sessionService: SessionService, private router: Router) {}

  changeLanguaje(languaje: 'es' | 'en' | 'it') {
    this.currentLanguaje = languaje;
    localStorage.setItem('languaje', languaje);
  }

  ngOnInit() {
    const save = localStorage.getItem('languaje') as 'es' | 'en' | 'it';
    this.currentLanguaje = save || 'es';
    this.loggedUser = this.sessionService.getUser();
    this.sessionService.user$.subscribe(user => {
      this.loggedUser = user;
    });
  }

  logout() {
    this.sessionService.logout();
    this.router.navigate(['/']);
  }

  toggleMenu() {
    this.openMenu = !this.openMenu;
  }
}
