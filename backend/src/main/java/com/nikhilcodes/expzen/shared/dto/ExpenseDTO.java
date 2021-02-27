package com.nikhilcodes.expzen.shared.dto;

import com.nikhilcodes.expzen.shared.enums.ExpenseType;
import lombok.Data;

import java.util.Date;


@Data
public class ExpenseDTO {
    public ExpenseDTO(
      float value,
      String description,
      ExpenseType expenseType,
      Date createdOn
    ) {
        this.value = value;
        this.description = description;
        this.expenseType = expenseType;
        this.createdOn = createdOn;
    }

    final float value;
    final Date createdOn;
    final String description;
    final ExpenseType expenseType;
}
