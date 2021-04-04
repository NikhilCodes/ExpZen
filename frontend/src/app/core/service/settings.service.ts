import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class SettingsService {
  private authUrl = `${environment.serverUrl}/auth`;

  constructor(private http: HttpClient) { }

  updateName(newName): Observable<void> {
    return this.http.post(
      `${this.authUrl}/user/name/update`,
      { name: newName },
      { withCredentials: true },
    ).pipe(
      map((value) => {
        // Nothing to return
        return;
      }),
    );
  }
}
