package com.nikhilcodes.creditzen.core.service;

import com.nikhilcodes.creditzen.shared.dto.ExpenseDTO;
import com.nikhilcodes.creditzen.core.repository.ExpenseRepository;
import com.nikhilcodes.creditzen.shared.enums.ExpenseType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDTO> getExpenseByUser(String uid) {
        List<ExpenseDTO> expenseData = new ArrayList<>();
        expenseRepository.findAllByUserId(uid).forEach(expense -> {
            expenseData.add(
              new ExpenseDTO(
                expense.getValue(),
                expense.getDescription(),
                expense.getExpenseType(),
                expense.getCreatedOn()
              )
            );
        });
        return expenseData;
    }
}
