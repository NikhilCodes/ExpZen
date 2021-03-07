package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, String> {
    List<Income> findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(String userId);
}
