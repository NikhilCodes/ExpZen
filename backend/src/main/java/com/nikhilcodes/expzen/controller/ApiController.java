package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.shared.dto.ServerTestResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.nikhilcodes.in"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping()
public class ApiController {

}
