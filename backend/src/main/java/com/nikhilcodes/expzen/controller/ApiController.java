package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.shared.dto.ServerTestResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://expzen.netlify.app", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/test")
public class ApiController {
    @GetMapping()
    ServerTestResponseDTO getServerActivity() {
        return new ServerTestResponseDTO("Server running gracefully! TEST");
    }
}
