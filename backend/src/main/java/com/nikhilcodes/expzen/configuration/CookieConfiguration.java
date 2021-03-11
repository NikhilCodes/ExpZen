package com.nikhilcodes.expzen.configuration;


import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CookieConfiguration implements WebMvcConfigurer {
    @Value("${spring.profiles.active}")
    private String serverProfile;

    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig() {
        return context -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();

            if (serverProfile.equals("prod")) {
                cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
            }

            context.setCookieProcessor(cookieProcessor);
        };
    }
}