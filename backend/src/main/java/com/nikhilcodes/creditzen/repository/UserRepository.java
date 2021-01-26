package com.nikhilcodes.creditzen.repository;

import com.nikhilcodes.creditzen.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
}
