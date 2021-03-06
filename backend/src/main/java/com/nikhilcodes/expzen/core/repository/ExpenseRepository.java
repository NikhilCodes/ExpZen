package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.Expense;
import com.nikhilcodes.expzen.shared.dto.AnalyticsDto.MonthlyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(String userId);

    @Query("select sum(value) from Expense where userId=:userId")
    Float getTotalExpenseValueByUserId(@Param("userId") String userId);

    @Query(value = "SELECT SUM(VALUE) FROM expense WHERE user_id=:userId AND YEAR(created_on)=YEAR(CURRENT_DATE()) AND MONTH(created_on)=MONTH(CURRENT_DATE())", nativeQuery = true)
    Float getTotalMonthlyExpenseValueByUserId(@Param("userId") String uid);

    @Query(value = "SELECT SUM(value), MONTH(created_on) FROM expense WHERE user_id=:userId AND YEAR(current_date())=YEAR(created_on) GROUP BY MONTH(created_on)", nativeQuery = true)
    List<List<Integer>> findExpensePerMonthForCurrentYear(@Param("userId") String uid);
}
