import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Subject } from 'rxjs';
import { User } from '../../shared/interface/user.interface';
import { environment } from '../../../environments/environment';
import { AuthTypes } from '../../shared/types/auth.types';
import { LoginResponse } from '../../shared/types/auth.types';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly userSubject = new BehaviorSubject<User>({});
  private readonly user$ = this.userSubject.asObservable();
  private readonly authStatusSubject = new BehaviorSubject<string>(AuthTypes.LOGGED_OUT);
  public readonly authStatus$ = this.authStatusSubject.asObservable();

  private authUrl = `${environment.serverUrl}/auth`;

  constructor(private http: HttpClient, private router: Router) {}

  public get user(): User {
    return this.userSubject.getValue();
  }

  public get authStatus(): string {
    return this.authStatusSubject.getValue();
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
