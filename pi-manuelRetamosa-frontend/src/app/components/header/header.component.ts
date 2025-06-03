import { Component } from '@angular/core';
import {RouterLink, RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";
import { SessionService} from '../../services/SessionService';
import { UserDTO } from '../../models/userDTO';
import { Router } from '@angular/router';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterModule, CommonModule, TranslatePipe],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  currentLanguage: 'es' | 'en' | 'it';
  openMenu = false;
  loggedUser: UserDTO | null = null;

  constructor(private translate: TranslateService, private sessionService: SessionService, private router: Router) {
    this.currentLanguage = (this.translate.currentLang || this.translate.getDefaultLang()) as 'es'|'en'|'it';
    this.sessionService.user$.subscribe(user => this.loggedUser = user);
  }

  ngOnInit() {
    this.loggedUser = this.sessionService.getUser();
  }

  changeLanguage(lang: 'es' | 'en' | 'it') {
    if (lang === this.currentLanguage) return;
    this.currentLanguage = lang;
    this.translate.use(lang);
    localStorage.setItem('lang', lang);
  }

  logout() {
    this.sessionService.logout();
    this.router.navigate(['/']);
  }

  toggleMenu() {
    this.openMenu = !this.openMenu;
  }
}

