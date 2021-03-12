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
}
