package com.nikhilcodes.creditzen.repository;

import com.nikhilcodes.creditzen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
}
