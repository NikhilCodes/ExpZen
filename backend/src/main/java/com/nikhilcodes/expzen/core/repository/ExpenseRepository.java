package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(String userId);
}
