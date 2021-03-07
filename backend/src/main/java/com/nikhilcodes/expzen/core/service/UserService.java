package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserDataResponse;
import com.nikhilcodes.expzen.model.User;
import com.nikhilcodes.expzen.model.UserAuth;
import com.nikhilcodes.expzen.core.repository.AuthRepository;
import com.nikhilcodes.expzen.core.repository.UserRepository;
import com.nikhilcodes.expzen.shared.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    public UserDTO loadUserByEmail(String email) throws UsernameNotFoundException {
        // Here we consider email as username!
        Optional<UserAuth> fetchedUserAuth = this.authRepository.findUserAuthByEmail(email);
        if (!fetchedUserAuth.isPresent()) {
            return null;
        }

        User fetchedUser = this.userRepository.findByUserId(fetchedUserAuth.get().getUserId());
        if (fetchedUser.getEnabled() != 1) {
            return null;
        }

        return new UserDTO(fetchedUser.getUserId(), email, fetchedUser.getName(), fetchedUser.getRoleType());
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
