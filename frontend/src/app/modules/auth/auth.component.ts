import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { animate, style, transition, trigger } from '@angular/animations';
import { AuthService } from '../../core/service/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ height: 0, opacity: 0 }),
            animate('0.3s ease-out',
              style({ height: 76, opacity: 1 })),
          ],
        ),
        transition(
          ':leave',
          [
            style({ height: 76, opacity: 1 }),
            animate('0.3s ease-in',
              style({ height: 0, opacity: 0 })),
          ],
        ),
      ],
    ),
  ],
})
export class AuthComponent implements OnInit {
  name = new FormControl('Nikhil Nayak');
  email = new FormControl('nikhil.nixel@gmail.com');
  password = new FormControl('123456');
  showPassword = false;

  authType: 'REGISTER' | 'LOGIN' = 'LOGIN';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    // alert(this.authService.test());
  }

  onSubmit(): void {
    if (this.authType === 'LOGIN') {
      this.onLogin();
    } else if (this.authType === 'REGISTER') {
      this.onRegister();
    }
  }

  onLogin(): void {
    this.authService.loginWithEmailAndPassword(this.email.value, this.password.value);
  }

  onRegister(): void {
    // this.authService.loginWithEmailAndPassword(this.email.value, this.password.value);
    // tslint:disable-next-line:no-unused-expression
    null;
  }
}
