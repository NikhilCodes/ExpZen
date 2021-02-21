package com.nikhilcodes.creditzen.core.service;

import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.UserDataResponse;
import com.nikhilcodes.creditzen.model.User;
import com.nikhilcodes.creditzen.model.UserAuth;
import com.nikhilcodes.creditzen.core.repository.AuthRepository;
import com.nikhilcodes.creditzen.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        // Here we consider email as username!
        UserAuth fetchedUserAuth = this.authRepository.findUserAuthByEmail(email);
        if (fetchedUserAuth == null) {
            return null;
        }

        User fetchedUser = this.userRepository.findByUserId(fetchedUserAuth.getUserId());
        if (fetchedUser.getEnabled() != 1) {
            return null;
        }

        return new org.springframework.security.core.userdetails.User(
          fetchedUserAuth.getEmail(),
          fetchedUserAuth.getPasskeyHashed(),
          Collections.singletonList(new SimpleGrantedAuthority(fetchedUser.getRoleType()))
        );
    }

    public UserDataResponse getUserDataByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return new UserDataResponse(
          user.getName(),
          email,
          user.getUserId()
        );
    }
}
