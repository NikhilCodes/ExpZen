package com.nikhilcodes.expzen.core.service;

import com.nikhilcodes.expzen.core.exceptions.UserAuthNotFoundException;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserAuthServiceResponse;
import com.nikhilcodes.expzen.model.User;
import com.nikhilcodes.expzen.core.repository.AuthRepository;
import com.nikhilcodes.expzen.core.repository.UserRepository;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserDataResponse;
import com.nikhilcodes.expzen.shared.dto.UserDTO;
import com.nikhilcodes.expzen.shared.util.Encoder;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
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

    public boolean userAlreadyExists(String email) {
        return this.authRepository.findUserAuthByEmail(email).isPresent();
    }

    public UserDataResponse createNewUser(String email, String password, String name) {
        String passwordHashed = this.passwordEncoder.encode(password);
        String userId = this.authRepository.createUser(email, passwordHashed, name);
        return new UserDataResponse(name, userId, email);
    }

    public UserAuthServiceResponse authenticate(String email, String password) {

        if (!this.passwordEncoder.matches(password, this.authRepository.getHashedPasswordByEmail(email))) {
            throw new BadCredentialsException("INVALID_CREDENTIAL_EXCEPTION");
        }

        final UserDTO userDetails = this.userService.getUserByEmail(email);

        UserAuthServiceResponse authSvcResp = new UserAuthServiceResponse();
        User user = this.userRepository.findUserByEmail(userDetails.getEmail());
        authSvcResp.setEmail(userDetails.getEmail());
        authSvcResp.setName(user.getName());
        authSvcResp.setUserId(user.getUserId());
        authSvcResp.setAccessToken(jwtUtil.generateAccessToken(userDetails.getUid()));
        authSvcResp.setRefreshToken(
          jwtUtil.generateRefreshToken(this.authRepository.getRefreshTokenByUid(userDetails.getUid()))
        );

        return authSvcResp;
    }

    public String refreshAuthentication(String expiredAccessToken, String refreshToken) throws UserAuthNotFoundException {
        String uid;

        if (jwtUtil.isTokenExpired(expiredAccessToken)) {
            uid = jwtUtil.extractSubject(expiredAccessToken);
            final UserDataResponse userDetails = this.userService.getUserDataByUid(uid);
            if (userDetails == null) {
                throw new UsernameNotFoundException(uid);
            }

            if (!jwtUtil.validateToken(refreshToken)) {
                throw new CredentialsExpiredException("Login expired, need to re-login.");
            }

            if (
              !this.authRepository.getRefreshTokenByUid(uid)
                .equals(jwtUtil.extractSubject(refreshToken))
            ) {
                throw new PreAuthenticatedCredentialsNotFoundException("Refresh token mismatch!");
            }

            return jwtUtil.generateAccessToken(userDetails.getUserId());
        } else {
            // We return the non-expired actually. Don't mind the name.
            return expiredAccessToken;
        }
    }

    public void updateEmail(String email, String uid) {
        this.authRepository.updateEmail(email, uid);
    }
}
