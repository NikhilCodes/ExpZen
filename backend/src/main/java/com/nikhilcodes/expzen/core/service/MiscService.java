package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.shared.dto.DashboardCumulativeDTO;
import org.springframework.stereotype.Service;

@Service
public class MiscService {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final DueService dueService;

    public MiscService(IncomeService incomeService, ExpenseService expenseService, DueService dueService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
        this.dueService = dueService;
    }

    public DashboardCumulativeDTO getBalanceMonthlyExpenseAndDue(String uid) {
        return new DashboardCumulativeDTO(
          this.incomeService.getTotalIncome(uid) - this.expenseService.getTotalExpense(uid),
          this.expenseService.getMonthlyExpense(uid),
          this.dueService.getTotalDue(uid)
        );
    }
}
