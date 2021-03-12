import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MiscService {
  private miscUrl = `${environment.serverUrl}/misc`;

  constructor(private http: HttpClient) { }

  public getBalanceMonthlyExpenseAndDue(): Observable<object> {
    return this.http.get(
      `${this.miscUrl}/balance-monthlyExpense-due`,
      { withCredentials: true },
    );
  }
}
