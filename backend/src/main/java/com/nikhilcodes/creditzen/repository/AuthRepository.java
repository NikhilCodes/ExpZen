package com.nikhilcodes.creditzen.repository;

import com.nikhilcodes.creditzen.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public interface AuthRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u")
    Collection<User> findAllUsers(Sort sort);

    //    @Query("SELECT user_id, name from user where name='Nikhil Nayak'")
    List<User> findAllByName(String name);
}
