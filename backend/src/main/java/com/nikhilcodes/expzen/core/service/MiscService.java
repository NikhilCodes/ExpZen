package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.shared.dto.DashboardCumulativeDTO;
import org.springframework.stereotype.Service;

@Service
public class MiscService {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public MiscService(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    public DashboardCumulativeDTO getBalanceMonthlyExpenseAndDue(String uid) {
        return new DashboardCumulativeDTO(
          this.incomeService.getTotalIncome(uid) - this.expenseService.getTotalExpense(uid),
          this.expenseService.getMonthlyExpense(uid),
          0F
        );
    }
}
