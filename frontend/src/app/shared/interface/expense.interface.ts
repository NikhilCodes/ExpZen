import { ExpenseTypes } from '../types/expense.types';

export interface ExpenseEntity {
  value: number;
  expenseType: ExpenseTypes;
  description: string;
  createdOn: Date | string;
}
