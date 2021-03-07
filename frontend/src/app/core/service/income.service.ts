import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IncomeEntity } from '../../shared/interface/income.interface';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class IncomeService {
  private incomeUrl = `${environment.serverUrl}/income`;

  constructor(private http: HttpClient) { }

  public findAllIncomesByUserId(): Observable<IncomeEntity[]> {
    return this.http.get(
      `${this.incomeUrl}`,
      { withCredentials: true },
    ).pipe(
      map((res: IncomeEntity[]) => {
        return res.map(value => {
          return { ...value, createdOn: new Date(value.createdOn) };
        });
      }),
    );
  }

  public createIncomeByUserId(incomeEntity: IncomeEntity): Observable<object> {
    return this.http.put(
      `${this.incomeUrl}`,
      incomeEntity,
      { withCredentials: true },
    );
  }
}
