package com.nikhilcodes.creditzen.core.repository;

import com.nikhilcodes.creditzen.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findAllByUserId(String userId);
}
