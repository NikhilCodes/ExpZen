package com.nikhilcodes.expzen.shared.dto;

import lombok.Data;

@Data
public class DashboardCumulativeDTO {
    Float balance;
    Float monthlyExpense;
    Float due;

    public DashboardCumulativeDTO(Float balance, Float monthlyExpense, Float due) {
        this.balance = balance;
        this.monthlyExpense = monthlyExpense;
        this.due = due;
    }
}
