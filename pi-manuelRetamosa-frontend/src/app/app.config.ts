import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideZoneChangeDetection} from '@angular/core';
import { provideRouter} from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient} from '@angular/common/http';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideClientHydration(withEventReplay()), provideHttpClient(withFetch()), importProvidersFrom(FormsModule), importProvidersFrom(TranslateModule.forRoot({loader: {provide: TranslateLoader, useFactory: HttpLoaderFactory, deps: [HttpClient]}}))]
};
