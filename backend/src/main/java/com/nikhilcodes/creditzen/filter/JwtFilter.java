package com.nikhilcodes.creditzen.filter;

import com.nikhilcodes.creditzen.constants.StringConstants;
import com.nikhilcodes.creditzen.service.UserService;
import com.nikhilcodes.creditzen.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    final private UserService userService;
    final private JwtUtil jwtUtil;

    @Autowired
    JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain
    ) throws ServletException, IOException {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies.length == 0) {
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
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userService.loadUserByUsername(email);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken emailPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                      userDetails, null, userDetails.getAuthorities()
                    );

                    emailPasswordAuthenticationToken.setDetails(
                      new WebAuthenticationDetailsSource().
                        buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(emailPasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException exception) {
            System.out.println(exception.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }
}
