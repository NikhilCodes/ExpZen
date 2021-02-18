package com.nikhilcodes.creditzen.repository;

import com.nikhilcodes.creditzen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);

    @Query(value = "select * from user where user_id=(select user_id from auth where email=:email)", nativeQuery = true)
    User findUserByEmail(String email);


}
