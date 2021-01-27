package com.nikhilcodes.creditzen.controller;

import com.nikhilcodes.creditzen.constants.NumberConstants;
import com.nikhilcodes.creditzen.constants.StringConstants;
import com.nikhilcodes.creditzen.dto.AuthenticationDto.AuthenticationBody;
import com.nikhilcodes.creditzen.dto.AuthenticationDto.UserRegBody;
import com.nikhilcodes.creditzen.service.AuthService;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public void authenticate(@RequestBody AuthenticationBody requestBody, HttpServletResponse response) throws Exception {
        List<String> jwtAtAndRt = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());
        String jwtAccessToken = jwtAtAndRt.get(0);
        String refreshToken = jwtAtAndRt.get(1);

        Cookie jwtAccessTokenCookie = new Cookie(StringConstants.JWT_AT_COOKIE_NAME, jwtAccessToken);
        jwtAccessTokenCookie.setMaxAge(NumberConstants.JWT_AT_COOKIE_MAX_AGE);
        jwtAccessTokenCookie.setHttpOnly(true); // Makes it accessible by server only.

        Cookie refreshTokenCookie = new Cookie(StringConstants.RT_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setMaxAge(NumberConstants.RT_COOKIE_MAX_AGE);
        refreshTokenCookie.setHttpOnly(true); // Makes it accessible by server only.

        response.addCookie(jwtAccessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    @PutMapping()
    public void register(@RequestBody UserRegBody requestBody) {
        authService.createNewUser(requestBody.getEmail(), requestBody.getPassword(), requestBody.getName());
    }

    @PatchMapping()
    public void setNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
