package com.nikhilcodes.creditzen.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserRegBody {
    private String email;
    private String password;
    private String name;
}
