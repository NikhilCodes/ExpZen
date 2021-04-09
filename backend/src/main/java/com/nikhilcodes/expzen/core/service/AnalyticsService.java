package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.core.repository.ExpenseRepository;
import com.nikhilcodes.expzen.core.repository.IncomeRepository;
import com.nikhilcodes.expzen.shared.dto.AnalyticsDto.MonthlyValue;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnalyticsService {
    IncomeRepository incomeRepository;
    ExpenseRepository expenseRepository;

    public AnalyticsService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<MonthlyValue> getMonthlyExpenseStats(String uid) {
        return this.expenseRepository
          .findExpensePerMonthForCurrentYear(uid)
          .stream()
          .map(numbers -> new MonthlyValue(numbers.get(0), numbers.get(1)))
          .collect(Collectors.toList());
    }
}
