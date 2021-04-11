package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, String> {
    List<Income> findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(String userId);

    @Query("select sum(value) from Income where userId=:userId")
    Float getTotalIncomeValueByUserId(@Param("userId") String userId);

    @Query(value = "SELECT SUM(value), MONTH(created_on) FROM income WHERE user_id=:userId AND YEAR(current_date())=YEAR(created_on) GROUP BY MONTH(created_on)", nativeQuery = true)
    List<List<Integer>> findIncomePerMonthForCurrentYear(@Param("userId") String uid);
}
