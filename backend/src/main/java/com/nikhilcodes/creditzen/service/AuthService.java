package com.nikhilcodes.creditzen.service;

import com.nikhilcodes.creditzen.repository.AuthRepository;
import com.nikhilcodes.creditzen.repository.UserRepository;
import com.nikhilcodes.creditzen.util.Encoder;
import com.nikhilcodes.creditzen.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        String passwordHashed = this.passwordEncoder.encode(password);
        String userId = this.authRepository.createUser(email, passwordHashed, name);
    }

    public List<String> authenticate(String email, String password) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        }

        final UserDetails userDetails = this.userService.loadUserByUsername(email);

        return Arrays.asList(jwtUtil.generateToken(userDetails), this.authRepository.getRefreshTokenByEmail(email));
    }

    public String refreshAuthentication(String expiredAccessToken, String refreshToken) {
        String email;

        if (jwtUtil.isTokenExpired(expiredAccessToken)) {
            email = jwtUtil.extractUserEmail(expiredAccessToken);
            final UserDetails userDetails = this.userService.loadUserByUsername(email);
            if (userDetails == null) {
                throw new UsernameNotFoundException(email);
            }

            if (!this.authRepository.getRefreshTokenByEmail(email).equals(refreshToken)) {
                throw new PreAuthenticatedCredentialsNotFoundException("Refresh token mismatch!");
            }

            return jwtUtil.generateToken(userDetails);
        } else {
            return expiredAccessToken;
        }
    }
}
