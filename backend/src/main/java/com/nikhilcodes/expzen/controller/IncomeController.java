package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.core.service.ExpenseService;
import com.nikhilcodes.expzen.core.service.IncomeService;
import com.nikhilcodes.expzen.shared.dto.IncomeDTO;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.nikhilcodes.in"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/income")
public class IncomeController {
    private final IncomeService incomeService;
    private final JwtUtil jwtUtil;

    public IncomeController(IncomeService incomeService, JwtUtil jwtUtil) {
        this.incomeService = incomeService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping()
    public List<IncomeDTO> getIncomesForCurrentUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.incomeService.getIncomeByUser(uid);
    }

    @PutMapping()
    public void addExpenseByUser(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken, @RequestBody IncomeDTO incomeDTO) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        this.incomeService.addIncomeByUser(uid, incomeDTO);
    }
}
