package com.nikhilcodes.expzen.shared.dto.AnalyticsDto;

import lombok.Data;

@Data
public class MonthlyValue {
    int value;
    int month; // Range [1:12]

    public MonthlyValue(int value, int month) {
        this.value = value;
        this.month = month;
    }
}
