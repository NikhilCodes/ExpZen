import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, tap } from 'rxjs/operators';
import { AuthService } from '../service/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  private handleJwtError(err: HttpErrorResponse, req?: HttpRequest<any>, next?: HttpHandler): Observable<any> {
    if (err.status === 401 && err.error.message === 'EXPIRED_JWT_TOKEN_EXCEPTION') {
      this.authService.refreshAccessToken();
      return next.handle(req);
    } else {
      this.authService.temporaryLogout();
      return throwError(err);
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(
        (err) => this.handleJwtError(err, req, next),
      ),
      retry(1),
    );
  }
}
