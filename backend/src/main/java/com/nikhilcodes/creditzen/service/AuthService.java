package com.nikhilcodes.creditzen.service;

import com.nikhilcodes.creditzen.dto.AuthenticationDto.JwtResponse;
import com.nikhilcodes.creditzen.repository.AuthRepository;
import com.nikhilcodes.creditzen.repository.UserRepository;
import com.nikhilcodes.creditzen.util.Encoder;
import com.nikhilcodes.creditzen.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(
      AuthenticationManager authenticationManager,
      AuthRepository authRepository,
      UserRepository userRepository,
      UserService userService,
      JwtUtil jwtUtil,
      Encoder encoder
    ) {
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = encoder.passwordEncoder();
    }

    public void createNewUser(String email, String password, String name) {
        String passwordHashed = passwordEncoder.encode(password);

        String userId = authRepository.createUser(email, passwordHashed, name);
    }

    public String authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        }

        final UserDetails userDetails = userService.loadUserByUsername(email);

        return jwtUtil.generateToken(userDetails);
    }

    public String refreshAuthentication(String expiredAccessToken) {
        String email;

        if (expiredAccessToken != null && jwtUtil.isTokenExpired(expiredAccessToken)) {
            email = jwtUtil.extractUserEmail(expiredAccessToken);
            final UserDetails userDetails = userService.loadUserByUsername(email);
            if (userDetails == null) {
                throw new UsernameNotFoundException(email);
            }
            return jwtUtil.generateToken(userDetails);
        } else {
            return expiredAccessToken;
        }
    }

    public String issueNewRefreshToken() {
        return "abc";
    }
}
