package com.nikhilcodes.creditzen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "User{" +
          "userId='" + userId + '\'' +
          ", name='" + name + '\'' +
          '}';
    }
}
