export type ExpenseTypes = 'Utilities' | 'Technology' | 'Everyday';

export interface ExpenseEntity {
  value: number;
  type: ExpenseTypes;
  description: string;
  createdOn: Date;
}
