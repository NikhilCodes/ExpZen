package com.nikhilcodes.expzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserDataResponse {
    String name;
    String email;
    String userId;
    String error;

    public UserDataResponse(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    public UserDataResponse(String error) {
        this.error = error;
    }
}
