package com.nikhilcodes.expzen.model;

import com.nikhilcodes.expzen.shared.dto.ExpenseDTO;
import com.nikhilcodes.expzen.shared.enums.ExpenseType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "expense")
public class Expense {
    public Expense() {
    }

    public Expense(ExpenseDTO expenseDTO) {
        this.value = expenseDTO.getValue();
        this.description = expenseDTO.getDescription();
        this.createdOn = expenseDTO.getCreatedOn();
        this.expenseType = expenseDTO.getExpenseType();
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String expenseId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private String description;

    private Float value;

    private Date createdOn;

    @CreationTimestamp()
    private Timestamp creationTimestamp;

    @Override
    public String toString() {
        return "UserAuth{" +
          "\n\texpenseId='" + expenseId + '\'' +
          ",\n\tuserId='" + userId + '\'' +
          ",\n\texpenseType='" + expenseType + '\'' +
          ",\n\tdescription='" + description + '\'' +
          ",\n\tvalue=" + value +
          ",\n\tcreatedOn=Date(" + createdOn + ')' +
          "\n}";
    }
}
