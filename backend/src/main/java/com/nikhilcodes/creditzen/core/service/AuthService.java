package com.nikhilcodes.creditzen.core.service;

import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.UserAuthServiceResponse;
import com.nikhilcodes.creditzen.model.User;
import com.nikhilcodes.creditzen.core.repository.AuthRepository;
import com.nikhilcodes.creditzen.core.repository.UserRepository;
import com.nikhilcodes.creditzen.shared.util.Encoder;
import com.nikhilcodes.creditzen.shared.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(
      AuthRepository authRepository,
      UserRepository userRepository,
      UserService userService,
      JwtUtil jwtUtil,
      Encoder encoder
    ) {
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

    public UserAuthServiceResponse authenticate(String email, String password) {

        if (!this.passwordEncoder.matches(password, this.authRepository.getHashedPasswordByEmail(email))) {
            throw new BadCredentialsException("INVALID_CREDENTIAL_EXCEPTION");
        }

        final UserDetails userDetails = this.userService.loadUserByEmail(email);

        UserAuthServiceResponse authSvcResp = new UserAuthServiceResponse();
        User user = this.userRepository.findUserByEmail(userDetails.getUsername());
        authSvcResp.setEmail(userDetails.getUsername());
        authSvcResp.setName(user.getName());
        authSvcResp.setUserId(user.getUserId());
        authSvcResp.setAccessToken(jwtUtil.generateToken(userDetails));
        authSvcResp.setRefreshToken(this.authRepository.getRefreshTokenByEmail(email));

        return authSvcResp;
    }

    public String refreshAuthentication(String expiredAccessToken, String refreshToken) {
        String email;

        if (jwtUtil.isTokenExpired(expiredAccessToken)) {
            email = jwtUtil.extractUserEmail(expiredAccessToken);
            final UserDetails userDetails = this.userService.loadUserByEmail(email);
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