import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { User } from '../../shared/interface/user.interface';
import { environment } from '../../../environments/environment';
import { AuthTypes } from '../../shared/types/auth.types';
import { LoginResponse } from '../../shared/interface/auth.interface';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly userSubject = new BehaviorSubject<User>({});
  private readonly user$ = this.userSubject.asObservable();
  private readonly authStatusSubject = new BehaviorSubject<string>(AuthTypes.LOADING);
  public readonly authStatus$ = this.authStatusSubject.asObservable();

  private authUrl = `${environment.serverUrl}/auth`;

  constructor(private http: HttpClient, private router: Router) { }

  public get user(): User {
    return this.userSubject.getValue();
  }

  public get authStatus(): string {
    return this.authStatusSubject.getValue();
  }

  public autoLoginWithCookies(): void {
    this.http.post(
      `${this.authUrl}/auto`,
      {},
      { withCredentials: true },
    ).subscribe((value: LoginResponse) => {
      this.userSubject.next(value);
      if (value.userId) {
        this.authStatusSubject.next(AuthTypes.LOGGED_IN);
      } else {
        this.authStatusSubject.next(AuthTypes.LOGGED_OUT);
        this.router.navigate(['login']);
      }
    });
  }

  public refreshAccessToken(): void {
    this.http.patch(this.authUrl, {}, { withCredentials: true })
      .subscribe();
  }

  public loginWithEmailAndPassword(email, password): void {
    this.http.post(this.authUrl, { email, password }, {
      withCredentials: true,
    }).subscribe((value: LoginResponse) => {
      this.userSubject.next(value);
      if (value.userId) {
        this.authStatusSubject.next(AuthTypes.LOGGED_IN);
        this.router.navigate(['/']);
      }
    });
  }
}
