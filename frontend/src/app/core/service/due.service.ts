import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DueEntity } from '../../shared/interface/due.interface';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DueService {
  private dueUrl = `${environment.serverUrl}/due`;

  constructor(private http: HttpClient) { }

  public findAllDues(): Observable<DueEntity[]> {
    console.log(this.dueUrl)
    return this.http.get(
      `${this.dueUrl}`,
      { withCredentials: true },
    ).pipe(
      map((res: DueEntity[]) => {
        return res.map(value => {
          return { ...value, createdOn: new Date(value.createdOn) };
        });
      }),
    );
  }

  public createDue(dueData: DueEntity): Observable<object> {
    return this.http.put(
      `${this.dueUrl}`,
      dueData,
      { withCredentials: true },
    );
  }
}
