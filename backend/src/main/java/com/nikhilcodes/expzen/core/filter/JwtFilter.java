package com.nikhilcodes.expzen.core.filter;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
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

        Cookie rtCookie = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals(StringConstants.RT_COOKIE_NAME))
          .findFirst()
          .orElse(null);

        if (rtCookie == null) {
            httpServletResponse.sendError(401, "REFRESH_TOKEN_NOT_FOUND");
            return;
        }

        String jwt = null;
        String uid = null;

        try {
            if (jwtCookie != null) {
                jwt = jwtCookie.getValue();
                uid = jwtUtil.extractSubject(jwt);
            } else {
                httpServletResponse.sendError(401, "User not authenticated!");
            }

            if (uid != null) {
                if (!jwtUtil.validateToken(jwt)) {
                    httpServletResponse.sendError(401, "EXPIRED_JWT_TOKEN_EXCEPTION");
                    return;
                }
            }
        } catch (SignatureException exception) {
            httpServletResponse.sendError(403, exception.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/auth") || path.equals("/api/auth/");
    }
}
