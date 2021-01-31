package com.nikhilcodes.creditzen.controller;

import com.nikhilcodes.creditzen.dto.ServerTestResponseDto.ServerTestResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/test")
public class ApiController {
    @GetMapping()
    ServerTestResponse getServerActivity() {
        return new ServerTestResponse("Server running gracefully! TEST");
    }
}
