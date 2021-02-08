package com.nikhilcodes.creditzen.filter;

import com.nikhilcodes.creditzen.constants.StringConstants;
import com.nikhilcodes.creditzen.service.UserService;
import com.nikhilcodes.creditzen.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final private JwtUtil jwtUtil;

    @Autowired
    JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain
    ) throws ServletException, IOException {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null) {
            cookies = new Cookie[]{};
        }
        Cookie jwtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.JWT_AT_COOKIE_NAME))
          .findFirst()
          .orElse(null);

        String jwt = null;
        String email = null;

        try {
            if (jwtCookie != null) {
                jwt = jwtCookie.getValue();
                email = jwtUtil.extractUserEmail(jwt);
            } else {
                httpServletResponse.sendError(401, "User not authenticated!");
            }

            if (email != null) {
                if (!jwtUtil.validateToken(jwt)) {
                    httpServletResponse.sendError(401, "EXPIRED_JWT_TOKEN_EXCEPTION");
                }
            }
        } catch (SignatureException exception) {
            httpServletResponse.sendError(403, exception.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/auth");
    }
}
