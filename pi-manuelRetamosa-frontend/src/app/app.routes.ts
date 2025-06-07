import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { BiographyComponent } from './components/biography/biography.component';
import { MusicComponent } from './components/music/music.component';
import { VideosComponent } from './components/videos/videos.component';
import { TourComponent } from './components/tour/tour.component';
import { ContactComponent } from './components/contact/contact.component';
import { ContestComponent } from './components/contest/contest.component';
import { MerchandisingComponent } from './components/merchandising/merchandising.component';
import { CartComponent } from './components/cart/cart.component';
import { LegalNoticeComponent } from './components/legal-notice/legal-notice.component';
import { PrivacyPolicyComponent } from './components/privacy-policy/privacy-policy.component';
import { CookiePolicyComponent } from './components/cookie-policy/cookie-policy.component';
import { AccessibilityStatementComponent } from './components/accessibility-statement/accessibility-statement.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ConfirmPurchaseComponent } from './components/confirm-purchase/confirm-purchase.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AuthGuard } from './guards/auth.guard';
import {MailSentComponent} from './components/mail-sent/mail-sent.component';
import {PurchaseMadeComponent} from './components/purchase-made/purchase-made.component';
import {EmailSentGuard} from './services/EmailSentGuard';
import {PanelAdminConcertComponent} from './components/panel-admin-concert/panel-admin-concert.component';
import {PanelAdminSongComponent} from './components/panel-admin-song/panel-admin-song.component';
import {PanelAdminAlbumComponent} from './components/panel-admin-album/panel-admin-album.component';
import {PanelAdminProductComponent} from './components/panel-admin-product/panel-admin-product.component';
import {AddedProductComponent} from './components/added-product/added-product.component';
import {
  PanelAdminProductVariantComponent
} from './components/panel-admin-product-variant/panel-admin-product-variant.component';

export const routes: Routes = [
  { path: '', component:HomeComponent, title: 'Dani J' },
  { path: 'biography', component:BiographyComponent, title: 'Biografía'},
  { path: 'music', component:MusicComponent, title: 'Música'},
  { path: 'videos', component:VideosComponent, title: 'Vídeos'},
  { path: 'tour', component:TourComponent, title: 'Tour Mundial'},
  { path: 'contact', component:ContactComponent, title: 'Contacto'},
  { path: 'contest', component:ContestComponent, title: 'Concursos'},
  { path: 'merchandising', component:MerchandisingComponent, title: 'Merchandising'},
  { path: 'cart', component: CartComponent, title: 'Carrito', canActivate: [AuthGuard]},
  { path: 'legal-notice', component:LegalNoticeComponent, title: 'Aviso Legal'},
  { path: 'privacy-policy', component:PrivacyPolicyComponent, title: 'Política de Privacidad'},
  { path: 'cookie-policy', component:CookiePolicyComponent, title: 'Política de Cookies'},
  { path: 'accessibility-statement', component:AccessibilityStatementComponent, title: 'Declaración de accesibilidad'},
  { path: 'product/:id', component: ProductDetailComponent, title: 'Producto'},
  { path: 'confirm-purchase', component: ConfirmPurchaseComponent, title: 'Confirmar Compra', canActivate: [AuthGuard]},
  { path: 'profile', component: ProfileComponent, title: 'Perfil', canActivate: [AuthGuard] },
  { path: 'mail-sent', component: MailSentComponent, title: 'Correo Enviado', canActivate: [ EmailSentGuard ] },
  { path: 'purchase-made', component: PurchaseMadeComponent, title: 'Compra Realizada' },
  { path: 'panel-admin-concerts', component: PanelAdminConcertComponent, title: 'Administración de Conciertos' },
  { path: 'panel-admin-songs', component: PanelAdminSongComponent, title: 'Administración de Canciones' },
  { path: 'panel-admin-albums', component: PanelAdminAlbumComponent, title: 'Administración de Álbumes' },
  { path: 'panel-admin-products', component: PanelAdminProductComponent, title: 'Administración de Productos' },
  { path: 'panel-admin-product-variants', component: PanelAdminProductVariantComponent, title: 'Administración de Variante de Productos' },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];
