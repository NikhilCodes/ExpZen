package com.nikhilcodes.creditzen.repository;

import com.nikhilcodes.creditzen.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;

public interface AuthRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u")
    Collection<User> findAllUsers(Sort sort);

    @Transactional
    @Modifying
    @Query(
      value = "INSERT INTO auth (user_id, email, passkey, refresh_token) VALUES (:userId, :email, :passHashed, :refreshToken)",
      nativeQuery = true
    )
    void createUserAuth(@Param("email") String email, @Param("passHashed") String passHashed, @Param("userId") String userId, @Param("refreshToken") String refreshToken);

    @Transactional
    @Modifying
    @Query(
      value = "INSERT INTO user (user_id, name) VALUES (:userId, :name)",
      nativeQuery = true
    )
    void createUserForReal(@Param("userId") String userId, @Param("name") String name);

    default String createUser(String email, String passHashed, String name) {
        String userId = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        createUserAuth(email, passHashed, userId, refreshToken);
        createUserForReal(userId, name);
        return userId;
    }

    //    @Query("SELECT user_id, name from user where name='Nikhil Nayak'")
    List<User> findAllByName(String name);
}
