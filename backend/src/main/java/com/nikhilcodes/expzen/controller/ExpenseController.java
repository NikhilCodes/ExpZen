package com.nikhilcodes.expzen.controller;

import com.nikhilcodes.expzen.shared.dto.AuthenticationDto.UserRegBody;
import com.nikhilcodes.expzen.shared.dto.ExpenseDTO;
import com.nikhilcodes.expzen.core.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://expzen.netlify.app", allowedHeaders = "*", allowCredentials = "true")
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

    @PutMapping("/{userId}")
    public void addExpenseByUser(@PathVariable("userId") String uid, @RequestBody ExpenseDTO expenseDTO) {
        expenseService.addExpenseByUser(uid, expenseDTO);
    }
}
