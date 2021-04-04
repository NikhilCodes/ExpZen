package com.nikhilcodes.expzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserDataResponse {
    String name;
    String userId;
    String email;
    String error;

    public UserDataResponse(String name, String userId, String email) {
        this.name = name;
        this.userId = userId;
        this.email = email;
    }

    public UserDataResponse(String error) {
        this.error = error;
    }
}
