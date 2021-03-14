package com.nikhilcodes.expzen.model;

import com.nikhilcodes.expzen.shared.dto.DueDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "due")
public class Due {
    public Due() {}

    public Due(DueDTO dueDTO) {
        this.value = dueDTO.getValue();
        this.description = dueDTO.getDescription();
        this.createdOn = dueDTO.getCreatedOn();
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String dueId;

    private String userId;

    private String description;

    private Float value;

    private Date createdOn;

    @CreationTimestamp()
    private Timestamp creationTimestamp;

    @Override
    public String toString() {
        return "Due{" +
          "\n\tdueId='" + dueId + '\'' +
          ",\n\tuserId='" + userId + '\'' +
          ",\n\tdescription='" + description + '\'' +
          ",\n\tvalue=" + value +
          ",\n\tcreatedOn=Date(" + createdOn + ')' +
          "\n}";
    }
}
