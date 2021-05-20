package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.constants.StringConstants;
import com.nikhilcodes.expzen.core.service.AnalyticsService;
import com.nikhilcodes.expzen.shared.dto.AnalyticsDto.MonthlyValue;
import com.nikhilcodes.expzen.shared.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://expzen.nikhilcodes.in"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/stats")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final JwtUtil jwtUtil;

    public AnalyticsController(AnalyticsService analyticsService, JwtUtil jwtUtil) {
        this.analyticsService = analyticsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("expense/monthly")
    public List<MonthlyValue> getMonthlyExpenseStats(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.analyticsService.getMonthlyExpenseStats(uid);
    }

    @GetMapping("income/monthly")
    public List<MonthlyValue> getMonthlyIncomeStats(@CookieValue(StringConstants.JWT_AT_COOKIE_NAME) String accessToken) {
        String uid = this.jwtUtil.extractSubject(accessToken);
        return this.analyticsService.getMonthlyIncomeStats(uid);
    }
}
