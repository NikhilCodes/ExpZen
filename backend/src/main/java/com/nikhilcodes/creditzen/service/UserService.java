package com.nikhilcodes.creditzen.service;

import com.nikhilcodes.creditzen.model.User;
import com.nikhilcodes.creditzen.model.UserAuth;
import com.nikhilcodes.creditzen.repository.AuthRepository;
import com.nikhilcodes.creditzen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
}
