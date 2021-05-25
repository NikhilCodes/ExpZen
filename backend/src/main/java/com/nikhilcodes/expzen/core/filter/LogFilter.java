package com.nikhilcodes.expzen.core.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@Component
public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain
    ) throws ServletException, IOException {
        float start = System.nanoTime();
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        float end = System.nanoTime();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        sd.setTimeZone(TimeZone.getTimeZone("IST"));

        System.out.printf(
          "%s %s %s %d - %s ms\n",
          sd.format(date),
          httpServletRequest.getMethod(),
          httpServletRequest.getRequestURI(),
          httpServletResponse.getStatus(),
          (end - start) / 1000000
        );
    }
}