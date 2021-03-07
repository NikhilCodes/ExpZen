package com.nikhilcodes.expzen.shared.dto;

import com.nikhilcodes.expzen.shared.enums.IncomeType;
import lombok.Data;

import java.util.Date;

@Data
public class IncomeDTO {
    public IncomeDTO(float value, IncomeType incomeType, Date createdOn) {
        this.value = value;
        this.incomeType = incomeType;
        this.createdOn = createdOn;
    }

    final float value;
    final Date createdOn;
    final IncomeType incomeType;
}
