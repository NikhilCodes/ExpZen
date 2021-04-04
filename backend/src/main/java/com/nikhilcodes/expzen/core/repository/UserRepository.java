package com.nikhilcodes.expzen.core.repository;

import com.nikhilcodes.expzen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);

    @Query(value = "select * from user where user_id=(select user_id from auth where email=:email)", nativeQuery = true)
    User findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User user set user.name=:newName where user.userId=:uid")
    void updateName(@Param("newName") String newName, @Param("uid") String userId);
}
