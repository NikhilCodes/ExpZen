import { Component } from '@angular/core';
import { AuthService } from './core/service/auth.service';
import { AuthTypes } from './shared/types/auth.types';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'CreditZen';
  authStatus = AuthTypes.LOADING;

  constructor(private authService: AuthService) {
    authService.authStatus$.subscribe(value => {
      this.authStatus = value;
    });
  }
}
