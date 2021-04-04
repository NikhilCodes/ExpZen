package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.core.exceptions.UserAuthNotFoundException;
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


    public UserDTO getUserByEmail(String email) throws UsernameNotFoundException {
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

    public UserDataResponse getUserDataByUid(String uid) throws UserAuthNotFoundException {
        User user = userRepository.findByUserId(uid);
        Optional<UserAuth> userAuth = authRepository.findUserAuthByUserId(uid);

        if (!userAuth.isPresent()) {
            throw new UserAuthNotFoundException(uid);
        }
        return new UserDataResponse(
          user.getName(),
          user.getUserId(),
          userAuth.get().getEmail()
        );
    }
}
