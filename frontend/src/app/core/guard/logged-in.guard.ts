import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { AuthTypes } from '../../shared/types/auth.types';

@Injectable({
  providedIn: 'root',
})
export class LoggedInGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.authService.authStatus$.subscribe(authStatus => {
      if (authStatus === AuthTypes.LOGGED_IN) {
        this.router.navigate(['/']);
        return false;
      }
    });

    return true;
  }
}
