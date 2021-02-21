package com.nikhilcodes.creditzen.controller;

import com.nikhilcodes.creditzen.constants.NumberConstants;
import com.nikhilcodes.creditzen.constants.StringConstants;
import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.AuthenticationBody;
import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.UserAuthServiceResponse;
import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.UserRegBody;
import com.nikhilcodes.creditzen.shared.dto.AuthenticationDto.UserDataResponse;
import com.nikhilcodes.creditzen.core.service.AuthService;
import com.nikhilcodes.creditzen.core.service.UserService;
import com.nikhilcodes.creditzen.shared.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
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

    @PostMapping()
    public UserDataResponse authenticate(@RequestBody AuthenticationBody requestBody, HttpServletResponse response) throws Exception {
        try {
            UserAuthServiceResponse userAuthResponse = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());

            String jwtAccessToken = userAuthResponse.getAccessToken();
            String refreshToken = userAuthResponse.getRefreshToken();

            Cookie jwtAccessTokenCookie = new Cookie(StringConstants.JWT_AT_COOKIE_NAME, jwtAccessToken);
            jwtAccessTokenCookie.setMaxAge(NumberConstants.JWT_AT_COOKIE_MAX_AGE);
//        jwtAccessTokenCookie.setPath("/");
            jwtAccessTokenCookie.setHttpOnly(true); // Makes it accessible by server only.

            Cookie refreshTokenCookie = new Cookie(StringConstants.RT_COOKIE_NAME, refreshToken);
            refreshTokenCookie.setMaxAge(NumberConstants.RT_COOKIE_MAX_AGE);
//        refreshTokenCookie.setPath("/");
            refreshTokenCookie.setHttpOnly(true); // Makes it accessible by server only.

            response.addCookie(jwtAccessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return new UserDataResponse(
              userAuthResponse.getName(),
              userAuthResponse.getEmail(),
              userAuthResponse.getUserId()
            );
        } catch (BadCredentialsException exception) {
            response.sendError(403, exception.getMessage());
            return new UserDataResponse(null, null, null);
        }
    }

    @PutMapping()
    public void register(@RequestBody UserRegBody requestBody) {
        authService.createNewUser(requestBody.getEmail(), requestBody.getPassword(), requestBody.getName());
    }

    @PostMapping("auto")
    public UserDataResponse autoAuthenticate(HttpServletRequest httpServletRequest) {
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
        String email = jwtUtil.extractUserEmail(jwt);

        return userService.getUserDataByEmail(email);
    }

    @PatchMapping()
    public void setNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new InsufficientAuthenticationException("You need to authenticate first!");
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

        response.addCookie(jwtAtCookie);
    }
}
