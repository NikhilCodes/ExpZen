package com.nikhilcodes.creditzen.controller;

import com.nikhilcodes.creditzen.shared.dto.ExpenseDTO;
import com.nikhilcodes.creditzen.core.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{userId}")
    public List<ExpenseDTO> getExpenseByUser(@PathVariable("userId") String uid) {
        return this.expenseService.getExpenseByUser(uid);
    }
}
