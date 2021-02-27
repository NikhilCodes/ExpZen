package com.nikhilcodes.expzen.shared.dto.AuthenticationDto;

import lombok.Data;

@Data
public class UserAuthServiceResponse {
    String name;
    String email;
    String userId;
    String accessToken;
    String refreshToken;
}
