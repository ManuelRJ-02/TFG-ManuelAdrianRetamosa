import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent, LoginComponent, RegisterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'pi-manuelRetamosa-frontend';
  constructor(translate: TranslateService) {
    translate.addLangs(['es', 'en', 'it']);
    translate.setDefaultLang('es');
    const saved = localStorage.getItem('lang');
    const browser = translate.getBrowserLang();
    translate.use(saved || browser || 'es');
  }
}


