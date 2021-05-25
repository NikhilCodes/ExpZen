package com.nikhilcodes.expzen.configuration;

import com.nikhilcodes.expzen.core.filter.JwtFilter;
import com.nikhilcodes.expzen.core.filter.LogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    final private JwtFilter jwtFilter;
    final private LogFilter logFilter;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtFilter, LogFilter logFilter) {
        this.jwtFilter = jwtFilter;
        this.logFilter = logFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .cors().and()
          .authorizeRequests()
          .antMatchers("/auth/**").permitAll()
          .and().exceptionHandling()
          .and().sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.logFilter, JwtFilter.class);
    }
}
