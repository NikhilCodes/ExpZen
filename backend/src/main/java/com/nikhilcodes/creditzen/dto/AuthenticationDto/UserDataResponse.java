package com.nikhilcodes.creditzen.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserDataResponse {
    String name;
    String email;
    String userId;

    public UserDataResponse(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }
}
