package com.nikhilcodes.creditzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class AuthenticationBody {
    private String email;
    private String password;
}
