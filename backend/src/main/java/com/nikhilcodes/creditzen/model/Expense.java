package com.nikhilcodes.creditzen.model;

import com.nikhilcodes.creditzen.shared.enums.ExpenseType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    String expenseId;

    String userId;

    @Enumerated(EnumType.STRING)
    ExpenseType expenseType;

    String description;

    Float value;

    Date createdOn;
}
