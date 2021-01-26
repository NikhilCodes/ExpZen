package com.nikhilcodes.creditzen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping()
    String getServerActivity() {
        return "Server running gracefully!";
    }
}
