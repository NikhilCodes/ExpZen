package com.nikhilcodes.expzen.shared.dto;

import lombok.Data;

@Data
public class UserDTO {
    String uid;
    String email;
    String name;
    String role;

    public UserDTO(String uid, String email, String name, String role) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
