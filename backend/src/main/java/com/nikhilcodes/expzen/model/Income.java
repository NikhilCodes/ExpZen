package com.nikhilcodes.expzen.model;

import com.nikhilcodes.expzen.shared.dto.IncomeDTO;
import com.nikhilcodes.expzen.shared.enums.IncomeType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "income")
public class Income {
    public Income() {}

    public Income(IncomeDTO incomeDTO) {
        this.value = incomeDTO.getValue();
        this.createdOn = incomeDTO.getCreatedOn();
        this.incomeType = incomeDTO.getIncomeType();
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String incomeId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;

    private Float value;

    private Date createdOn;

    @CreationTimestamp()
    private Timestamp creationTimestamp;

    @Override
    public String toString() {
        return "Income{" +
          "\n\tincomeId='" + incomeId + '\'' +
          ",\n\tuserId='" + userId + '\'' +
          ",\n\tincomeType='" + incomeType + '\'' +
          ",\n\tvalue=" + value +
          ",\n\tcreatedOn=Date(" + createdOn + ')' +
          "\n}";
    }
}
