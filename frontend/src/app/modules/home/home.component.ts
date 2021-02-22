import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ExpenseEntity } from '../../shared/interface/expense.interface';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ExpenseService } from '../../core/service/expense.service';
import { AuthService } from '../../core/service/auth.service';
import { ExpenseTypes } from '../../shared/types/expense.types';
import { FormControl } from '@angular/forms';
import { toTitleCase } from 'codelyzer/util/utils';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements AfterViewInit {
  income: number;
  expense: number;
  due: number;

  displayedColumns: string[] = ['createdOn', 'value', 'expenseType', 'description'];
  expenseTypesList = Object.values(ExpenseTypes);

  isLoadingExpenseData = true;
  isAddFundModalVisible = false;

  // Form Value
  formValue = new FormControl(null);
  formDescription = new FormControl('');
  formCreatedOn = new FormControl(new Date(Date.now()));
  formCategory = new FormControl(null);

  dataSource = new MatTableDataSource<ExpenseEntity>([]);

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  constructor(private expenseService: ExpenseService, private authService: AuthService) {
    this.income = 8000;
    this.expense = 0;
    this.due = 0;
  }

  ngAfterViewInit(): void {
    this.loadAllExpensesForUser();
    this.dataSource.paginator = this.paginator;
  }

  loadAllExpensesForUser(): void {
    this.expenseService.findAllExpensesByUserId(this.authService.user.userId)
      .subscribe((value) => {
        this.isLoadingExpenseData = false;
        this.dataSource.data = value;
      });
  }

  showAddFundModal(): void {
    this.isAddFundModalVisible = true;
  }

  closeAddFundModal(): void {
    this.isAddFundModalVisible = false;
  }

  clearForm(): void {
    this.formValue.setValue(null);
    this.formDescription.setValue('');
    this.formCreatedOn.setValue(new Date(Date.now()));
    this.formCategory.setValue(null);
  }

  onSubmitCreateExpenseForm(): void {
    this.expenseService.createExpenseByUserId(this.authService.user.userId, {
      value: this.formValue.value,
      description: this.formDescription.value,
      expenseType: this.formCategory.value,
      createdOn: this.formCreatedOn.value,
    }).subscribe(_ => {
      this.clearForm();
      this.loadAllExpensesForUser();
      this.closeAddFundModal();
    });

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
