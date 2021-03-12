package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.core.service.ExpenseService;
import com.nikhilcodes.expzen.core.service.IncomeService;
import com.nikhilcodes.expzen.core.service.MiscService;
import com.nikhilcodes.expzen.shared.dto.DashboardCumulativeDTO;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.netlify.app"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/misc")
public class MiscController {
    private final MiscService miscService;
    private final JwtUtil jwtUtil;

    public MiscController(MiscService miscService, JwtUtil jwtUtil) {
        this.miscService = miscService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/balance-monthlyExpense-due") // balance-expense-due [bed]
    public DashboardCumulativeDTO getCumulativeBED(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.miscService.getBalanceMonthlyExpenseAndDue(uid);
    }
}
