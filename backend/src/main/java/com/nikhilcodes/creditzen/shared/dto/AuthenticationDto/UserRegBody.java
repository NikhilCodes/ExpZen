package com.nikhilcodes.creditzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserRegBody {
    private String email;
    private String password;
    private String name;
}
