package com.nikhilcodes.creditzen.core.repository;

import com.nikhilcodes.creditzen.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<UserAuth, String> {
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
      value = "INSERT INTO user (user_id, name, role) VALUES (:userId, :name, 'USER')",
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

    UserAuth findUserAuthByEmail(String email);

    default String getRefreshTokenByEmail(String email) {
        return findUserAuthByEmail(email).getRefreshToken();
    }

    default String getHashedPasswordByEmail(String email) {
        return findUserAuthByEmail(email).getPasskeyHashed();
    }
}