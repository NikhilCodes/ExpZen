package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.User;
import com.nikhilcodes.expzen.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    Optional<UserAuth> findUserAuthByEmail(String email);

    Optional<UserAuth> findUserAuthByUserId(String uid);

    @Transactional
    @Modifying
    @Query("update UserAuth auth set auth.email=:newEmail where auth.userId=:uid")
    void updateEmail(@Param("newEmail") String newEmail, @Param("uid") String userId);

    default String getRefreshTokenByUid(String uid) {
        return findUserAuthByUserId(uid).get().getRefreshToken();
    }

    default String getHashedPasswordByEmail(String email) {
        Optional<UserAuth> fetchedUserAuth = findUserAuthByEmail(email);
        if (!fetchedUserAuth.isPresent()) {
            return "";
        }
        return fetchedUserAuth.get().getPasskeyHashed();
    }
}
