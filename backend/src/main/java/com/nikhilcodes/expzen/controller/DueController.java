package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.core.service.DueService;
import com.nikhilcodes.expzen.shared.dto.DueDTO;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.netlify.app"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/due")
public class DueController {
    private final DueService dueService;
    private final JwtUtil jwtUtil;

    public DueController(DueService dueService, JwtUtil jwtUtil) {
        this.dueService = dueService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping()
    public List<DueDTO> getDueByUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.dueService.getDueByUser(uid);
    }

    @PutMapping()
    public void addDueByUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken, @RequestBody DueDTO dueDTO) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        this.dueService.addDueByUser(uid, dueDTO);
    }
}
