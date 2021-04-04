package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.NumberConstants;
import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.core.exceptions.UserAuthNotFoundException;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.AuthenticationBody;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserAuthServiceResponse;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserRegBody;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserDataResponse;
import com.nikhilcodes.expzen.core.service.AuthService;
import com.nikhilcodes.expzen.core.service.UserService;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.netlify.app"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, UserService userService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Value("${spring.profiles.active}")
    private String serverProfile;

    @PostMapping()
    public UserDataResponse authenticate(@RequestBody AuthenticationBody requestBody, HttpServletResponse response) throws Exception {
        try {
            UserAuthServiceResponse userAuthResponse = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());

            String jwtAccessToken = userAuthResponse.getAccessToken();
            String refreshToken = userAuthResponse.getRefreshToken();

            Cookie jwtAccessTokenCookie = new Cookie(StringConstants.JWT_AT_COOKIE_NAME, jwtAccessToken);
            jwtAccessTokenCookie.setMaxAge(NumberConstants.JWT_AT_COOKIE_MAX_AGE);
            jwtAccessTokenCookie.setHttpOnly(true); // Makes it accessible by server only.
            if (serverProfile.equals("prod")) {
                jwtAccessTokenCookie.setSecure(true);
            }


            Cookie refreshTokenCookie = new Cookie(StringConstants.RT_COOKIE_NAME, refreshToken);
            refreshTokenCookie.setMaxAge(NumberConstants.JWT_RT_COOKIE_MAX_AGE);
            refreshTokenCookie.setHttpOnly(true); // Makes it accessible by server only.
            if (serverProfile.equals("prod")) {
                refreshTokenCookie.setSecure(true);
            }

            response.addCookie(jwtAccessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return new UserDataResponse(
              userAuthResponse.getName(),
              userAuthResponse.getUserId(),
              userAuthResponse.getEmail()
            );
        } catch (BadCredentialsException exception) {
            response.sendError(403, exception.getMessage());
            return new UserDataResponse("BAD_CREDENTIAL_ERROR"); // This line doesn't really matter! Just need to Return UserDataResponse
        }
    }

    @PutMapping()
    public UserDataResponse register(@RequestBody UserRegBody requestBody) throws KeyAlreadyExistsException {
        if (this.authService.userAlreadyExists(requestBody.getEmail())) {
            throw new KeyAlreadyExistsException("EMAIL_ALREADY_EXISTS");
        }

        try {
            return this.authService.createNewUser(
              requestBody.getEmail(),
              requestBody.getPassword(),
              requestBody.getName()
            );
        } catch (Exception e) {
            throw new KeyAlreadyExistsException("SOME_INTERNAL_ERROR_OCCURRED");
        }
    }

    @PostMapping("auto")
    public UserDataResponse autoAuthenticate(HttpServletRequest httpServletRequest) throws UserAuthNotFoundException {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            cookies = new Cookie[]{};
        }
        Cookie jwtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.JWT_AT_COOKIE_NAME))
          .findFirst()
          .orElse(null);

        if (jwtCookie == null) {
            return new UserDataResponse(null, null, null);
        }

        String jwt = jwtCookie.getValue();
        String uid = jwtUtil.extractSubject(jwt);

        return userService.getUserDataByUid(uid);
    }

    @PatchMapping()
    public void setNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws UserAuthNotFoundException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("You need to authenticate!");
        }

        Cookie jwtAtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.JWT_AT_COOKIE_NAME))
          .findFirst()
          .orElse(null);

        Cookie rtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.RT_COOKIE_NAME))
          .findFirst()
          .orElse(null);

        if (rtCookie == null || jwtAtCookie == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("You need to authenticate!");
        }

        String newJwt = authService.refreshAuthentication(jwtAtCookie.getValue(), rtCookie.getValue());
        jwtAtCookie = new Cookie(StringConstants.JWT_AT_COOKIE_NAME, newJwt);
        jwtAtCookie.setMaxAge(NumberConstants.JWT_AT_COOKIE_MAX_AGE);
        jwtAtCookie.setHttpOnly(true); // Makes it accessible by server only.
        if (serverProfile.equals("prod")) {
            jwtAtCookie.setSecure(true);
        }
        response.addCookie(jwtAtCookie);
    }

    @PostMapping("user/name/update")
    public void updatePersonalInfo(@RequestBody UserRegBody requestBody, @CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        this.userService.updateName(requestBody.getName(), uid);
    }

    @DeleteMapping("logout")
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtAccessCookie = new Cookie(StringConstants.JWT_AT_COOKIE_NAME, null);
        jwtAccessCookie.setMaxAge(0);
        jwtAccessCookie.setHttpOnly(true);
        jwtAccessCookie.setPath("/api");

        Cookie refreshCookie = new Cookie(StringConstants.RT_COOKIE_NAME, null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/api");

        if (serverProfile.equals("prod")) {
            jwtAccessCookie.setSecure(true);
            refreshCookie.setSecure(true);
        }

        response.addCookie(jwtAccessCookie);
        response.addCookie(refreshCookie);
    }
}
