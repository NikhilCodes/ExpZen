import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { animate, style, transition, trigger } from '@angular/animations';
import { AuthService } from '../../core/service/auth.service';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

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
            animate('0.3s ease-in',
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
export class AuthComponent {
  name = new FormControl('Nikhil Nayak');
  email = new FormControl('1928239@kiit.ac.in');
  password = new FormControl('123456');
  showPassword = false;

  authType: 'REGISTER' | 'LOGIN' = 'LOGIN';

  constructor(private authService: AuthService, private snackBar: MatSnackBar) { }

  onSubmit(): void {
    if (this.authType === 'LOGIN') {
      this.onLogin();
    } else if (this.authType === 'REGISTER') {
      this.onRegister();
    }
  }

  onLogin(): void {
    this.authService
      .loginWithEmailAndPassword(this.email.value, this.password.value)
      .pipe(
        catchError((err) => {
          if (err.error.message === 'INVALID_CREDENTIAL_EXCEPTION') {
            this.snackBar.open('Invalid Email or Password!', '', { duration: 2000 });
          }

          return throwError(err);
        }),
      ).subscribe();
  }

  onRegister(): void {
    this.authService.registerUserWithNameEmailAndPassword(this.name.value, this.email.value, this.password.value)
      .subscribe(_ => {
        this.onLogin();
      });
  }
}
