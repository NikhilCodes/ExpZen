import { IncomeTypes } from '../types/income.types';

export interface IncomeEntity {
  value: number;
  incomeType: IncomeTypes;
  createdOn: Date | string;
}
