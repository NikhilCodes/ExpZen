package com.nikhilcodes.expzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserDataResponse {
    String name;
    String userId;
    String error;

    public UserDataResponse(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public UserDataResponse(String error) {
        this.error = error;
    }
}
