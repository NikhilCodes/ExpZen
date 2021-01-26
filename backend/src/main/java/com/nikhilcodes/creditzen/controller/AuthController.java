package com.nikhilcodes.creditzen.controller;

import com.nikhilcodes.creditzen.constants.NumberConstants;
import com.nikhilcodes.creditzen.constants.StringConstants;
import com.nikhilcodes.creditzen.dto.AuthenticationDto.AuthenticationBody;
import com.nikhilcodes.creditzen.repository.AuthRepository;
import com.nikhilcodes.creditzen.service.AuthService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthRepository authRepository;
    private final AuthService authService;

    public AuthController(AuthRepository authRepository, AuthService authService) {
        this.authRepository = authRepository;
        this.authService = authService;
    }

    @PostMapping()
    public void authenticate(@RequestBody AuthenticationBody requestBody, HttpServletResponse response) throws Exception {
        String jwt = authService.authenticate(requestBody.getEmail(), requestBody.getPassword());
        Cookie jwtAccessTokenCookie = new Cookie(StringConstants.JWT_COOKIE_NAME, jwt);
        jwtAccessTokenCookie.setMaxAge(NumberConstants.JWT_COOKIE_MAX_AGE);
        jwtAccessTokenCookie.setHttpOnly(true); // Makes it accessible by server only.

        response.addCookie(jwtAccessTokenCookie);
    }

    @PatchMapping()
    public void setNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendError(401, "You need to authenticate!");
            return;
        }

        Cookie jwtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.JWT_COOKIE_NAME))
          .findFirst()
          .orElse(null);
        String newJwt = authService.refreshAuthentication(jwtCookie.getValue());
        jwtCookie = new Cookie(StringConstants.JWT_COOKIE_NAME, newJwt);
        jwtCookie.setMaxAge(NumberConstants.JWT_COOKIE_MAX_AGE);
        jwtCookie.setHttpOnly(true); // Makes it accessible by server only.

        response.addCookie(jwtCookie);
    }

    @GetMapping()
    void getUsers() {
        System.out.println(this.authRepository.findAllUsers(Sort.by(Sort.Direction.ASC, "name")));
    }

    @GetMapping(path = "nik")
    void getNikhil() {
        System.out.println(this.authRepository.findAllByName("Nikhil Nayak"));
    }
}
