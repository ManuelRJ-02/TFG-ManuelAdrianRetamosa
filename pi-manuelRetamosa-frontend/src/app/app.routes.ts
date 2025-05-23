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

export const routes: Routes = [
  { path: '', component:HomeComponent },
  { path: 'biography', component:BiographyComponent},
  { path: 'music', component:MusicComponent},
  { path: 'videos', component:VideosComponent},
  { path: 'tour', component:TourComponent},
  { path: 'contact', component:ContactComponent},
  { path: 'contest', component:ContestComponent},
  { path: 'merchandising', component:MerchandisingComponent},
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'legal-notice', component:LegalNoticeComponent},
  { path: 'privacy-policy', component:PrivacyPolicyComponent},
  { path: 'cookie-policy', component:CookiePolicyComponent},
  { path: 'accessibility-statement', component:AccessibilityStatementComponent},
  { path: 'product/:id', component: ProductDetailComponent},
  { path: 'confirm-purchase', component: ConfirmPurchaseComponent, canActivate: [AuthGuard]},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];
