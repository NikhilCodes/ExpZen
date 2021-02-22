import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ExpenseEntity } from '../../shared/interface/expense.interface';
import { Observable } from 'rxjs';
import { filter, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private authUrl = `${environment.serverUrl}/expense`;

  constructor(private http: HttpClient) { }

  public findAllExpensesByUserId(uid: string): Observable<ExpenseEntity[]> {
    return this.http.get(
      `${this.authUrl}/${uid}`,
      { withCredentials: true },
    ).pipe(
      map((res: ExpenseEntity[]) => {
        return res.map(value => {
          return { ...value, createdOn: new Date(value.createdOn) };
        });
      }),
    );
  }

  public createExpenseByUserId(uid: string, expenseData: ExpenseEntity): Observable<object> {
    return this.http.put(
      `${this.authUrl}/${uid}`,
      expenseData,
      { withCredentials: true },
    );
  }
}
