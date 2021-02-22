package com.nikhilcodes.creditzen.model;

import lombok.Data;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private String userId;

    private String name;

    @Column(name = "role")
    private String roleType; // Has to be either "ADMIN" or "USER"

    private int enabled;

    @Override
    public String toString() {
        return "UserAuth{" +
          "\n\tuserId='" + userId + '\'' +
          ",\n\tname='" + name + '\'' +
          ",\n\trole='" + roleType + '\'' +
          ",\n\tenabled=" + enabled +
          "\n}";
    }
}
