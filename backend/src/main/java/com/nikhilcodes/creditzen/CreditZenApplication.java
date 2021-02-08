package com.nikhilcodes.creditzen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CreditZenApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditZenApplication.class, args);
    }

}
