import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ExpenseEntity } from '../../shared/interface/expense.interface';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements AfterViewInit {
  income: number;
  expense: number;
  due: number;

  displayedColumns: string[] = ['createdOn', 'value', 'type', 'description'];

  expenseData: ExpenseEntity[] = [];

  dataSource = new MatTableDataSource<ExpenseEntity>([]);
  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  constructor() {
    this.income = 8000;
    this.expense = 0;
    this.due = 0;
  }

  ngAfterViewInit(): void {
    this.expenseData = [
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Everyday' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },
      { value: 3000, createdOn: new Date(Date.now()), description: 'Test', type: 'Technology' },

    ];
    this.dataSource.data = this.expenseData;
    this.dataSource.paginator = this.paginator;
  }

  getNumberAsPrice(num: number): string {
    let price = '';

    if (num === 0) {
      return '0';
    }

    let count = 0;
    while (num) {
      if (count >= 3 && count % 2 === 1) {
        price = ',' + price;
      }
      price = (num % 10) + price;
      num = Math.floor(num / 10);
      count++;
    }
    return price;
  }
}
