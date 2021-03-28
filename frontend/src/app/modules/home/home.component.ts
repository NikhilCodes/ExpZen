import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { ExpenseEntity } from '../../shared/interface/expense.interface';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ExpenseService } from '../../core/service/expense.service';
import { ExpenseTypes } from '../../shared/types/expense.types';
import { FormControl } from '@angular/forms';
import { IncomeTypes } from '../../shared/types/income.types';
import { IncomeService } from '../../core/service/income.service';
import { IncomeEntity } from '../../shared/interface/income.interface';
import { BalanceMonthlyExpenseDue } from '../../shared/interface/misc.interface';
import { MiscService } from '../../core/service/misc.service';
import { DueService } from '../../core/service/due.service';
import { DueEntity } from '../../shared/interface/due.interface';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements AfterViewInit {
  // @Input()
  // viewMode: boolean;

  balance: number;
  monthlyExpense: number;
  due: number;

  displayedIncomeColumns: string[] = ['createdOn', 'value', 'incomeType'];
  displayedExpenseColumns: string[] = ['createdOn', 'value', 'expenseType', 'description'];
  displayedDueColumns: string[] = ['createdOn', 'value', 'description'];

  incomeTypesList = Object.values(IncomeTypes);
  expenseTypesList = Object.values(ExpenseTypes);

  isLoadingFundsData = true;
  isAddFundsModalVisible = false;

  isLoadingExpenseData = true;
  isAddExpenseModalVisible = false;

  isLoadingDueData = true;
  isAddDueModalVisible = false;

  // Form Value
  formValue = new FormControl(null);
  formDescription = new FormControl('');
  formCreatedOn = new FormControl(new Date(Date.now()));
  formCategory = new FormControl(null);

  dataSourceExpense = new MatTableDataSource<ExpenseEntity>([]);
  dataSourceFunds = new MatTableDataSource<IncomeEntity>([]);
  dataSourceDues = new MatTableDataSource<DueEntity>([]);

  @ViewChild(MatPaginator, { static: true })
  paginatorExpense: MatPaginator;

  @ViewChild(MatPaginator, { static: true })
  paginatorFunds: MatPaginator;

  @ViewChild(MatPaginator, { static: true })
  paginatorDues: MatPaginator;

  dataTableViewMode = 1; // 2 for dues, 1 for expense and 0 for funds added

  constructor(
    private incomeService: IncomeService,
    private expenseService: ExpenseService,
    private dueService: DueService,
    private miscService: MiscService,
  ) {}

  ngAfterViewInit(): void {
    this.miscService.getBalanceMonthlyExpenseAndDue()
      .subscribe((value: BalanceMonthlyExpenseDue) => {
        this.balance = value.balance;
        this.monthlyExpense = value.monthlyExpense;
        this.due = value.due;
      });

    // NOTE: The order followed below for `Loading Data` matters, as it determines the paginator initial values.
    this.loadAllFundsForUser();
    this.loadAllDuesForUser();
    this.loadAllExpensesForUser();

    this.dataSourceFunds.paginator = this.paginatorFunds;
    this.dataSourceDues.paginator = this.paginatorDues;
    this.dataSourceExpense.paginator = this.paginatorExpense;
  }

  loadAllExpensesForUser(): void {
    this.expenseService.findAllExpenses()
      .subscribe((value) => {
        this.isLoadingExpenseData = false;
        this.dataSourceExpense.data = value;
      });
  }

  loadAllFundsForUser(): void {
    this.incomeService.findAllIncomes()
      .subscribe((value) => {
        this.isLoadingFundsData = false;
        this.dataSourceFunds.data = value;
      });
  }

  loadAllDuesForUser(): void {
    this.dueService.findAllDues()
      .subscribe((value) => {
        this.isLoadingDueData = false;
        this.dataSourceDues.data = value;
      });
  }

  showAddFundsModal(): void {
    this.isAddFundsModalVisible = true;
  }

  closeAddFundsModal(): void {
    this.isAddFundsModalVisible = false;
  }

  showAddExpenseModal(): void {
    this.isAddExpenseModalVisible = true;
  }

  closeAddExpenseModal(): void {
    this.isAddExpenseModalVisible = false;
  }

  showAddDueModal(): void {
    this.isAddDueModalVisible = true;
  }

  closeAddDueModal(): void {
    this.isAddDueModalVisible = false;
  }

  clearForm(): void {
    this.formValue.setValue(null);
    this.formDescription.setValue('');
    this.formCreatedOn.setValue(new Date(Date.now()));
    this.formCategory.setValue(null);
  }

  onSubmitCreateExpenseForm(): void {
    this.expenseService.createExpense({
      value: this.formValue.value,
      description: this.formDescription.value,
      expenseType: this.formCategory.value,
      createdOn: this.formCreatedOn.value,
    }).subscribe(_ => {
      this.clearForm();
      this.loadAllExpensesForUser();
      this.closeAddExpenseModal();
    });
  }

  onSubmitCreateDueForm(): void {
    this.dueService.createDue({
      value: this.formValue.value,
      description: this.formDescription.value,
      createdOn: this.formCreatedOn.value,
    }).subscribe(_ => {
      this.clearForm();
      this.loadAllDuesForUser();
      this.refreshBalance();
      this.closeAddDueModal();
    });
  }

  onSubmitCreateFundsForm(): void {
    this.incomeService.createIncome({
      value: this.formValue.value,
      incomeType: this.formCategory.value,
      createdOn: this.formCreatedOn.value,
    }).subscribe(_ => {
      this.clearForm();
      this.loadAllFundsForUser();
      this.closeAddFundsModal();
    });
  }

  refreshBalance(): void {

  }

  getNumberAsPrice(num: number): string {
    let price = '';
    let isNeg = false;
    if (num < 0) {
      isNeg = true;
      num = -num;
    }
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
    return (isNeg ? '-' : '') + price;
  }
}
