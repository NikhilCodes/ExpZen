package com.nikhilcodes.expzen.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "auth")
public class UserAuth {
    @Column(name = "passkey")
    private String passkeyHashed;

    private String userId;

    @Id
    private String email;

    private String refreshToken;

    @Override
    public String toString() {
        return "UserAuth{" +
          "\n\tpasskeyHashed='" + passkeyHashed + '\'' +
          ",\n\tuserId='" + userId + '\'' +
          ",\n\temail='" + email + '\'' +
          ",\n\trefreshToken='" + refreshToken + '\'' +
          "\n}";
    }
}