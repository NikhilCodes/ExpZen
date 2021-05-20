package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserRegBody;
import com.nikhilcodes.expzen.shared.dto.ExpenseDTO;
import com.nikhilcodes.expzen.core.service.ExpenseService;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.nikhilcodes.in"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final JwtUtil jwtUtil;

    public ExpenseController(ExpenseService expenseService, JwtUtil jwtUtil) {
        this.expenseService = expenseService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping()
    public List<ExpenseDTO> getExpensesByUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.expenseService.getExpenseByUser(uid);
    }

    @PutMapping()
    public void addExpenseByUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken, @RequestBody ExpenseDTO expenseDTO) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        this.expenseService.addExpenseByUser(uid, expenseDTO);
    }
}
