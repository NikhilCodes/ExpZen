package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.Due;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DueRepository extends JpaRepository<Due, String> {
    List<Due> findAllByUserIdOrderByCreatedOnDescCreationTimestampDesc(String userId);

    @Query("select sum(value) from Due where userId=:userId")
    Float getTotalDueValueByUserId(@Param("userId") String userId);
}
