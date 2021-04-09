import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MonthlyValue } from '../../shared/interface/analytics.interface';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {
  private analyticsUrl = `${environment.serverUrl}/stats`;

  constructor(private http: HttpClient) { }

  public getMonthlyExpenseStats(): Observable<MonthlyValue[]> {
    return this.http.get(
      `${this.analyticsUrl}/expense/monthly`,
      { withCredentials: true },
    ).pipe(
      map((res: MonthlyValue[]) => {
        console.log(res);
        return res;
      }),
    );
  }
}
