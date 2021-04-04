import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './modules/auth/auth.component';
import { HomeComponent } from './modules/home/home.component';
import { AuthGuard } from './core/guard/auth.guard';
import { LoggedInGuard } from './core/guard/logged-in.guard';
import { SettingsComponent } from './modules/settings/settings.component';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: AuthComponent, canActivate: [LoggedInGuard] },
  { path: 'settings', component: SettingsComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
