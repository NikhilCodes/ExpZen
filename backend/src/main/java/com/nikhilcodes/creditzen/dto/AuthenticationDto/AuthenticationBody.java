package com.nikhilcodes.creditzen.dto.AuthenticationDto;

public class AuthenticationBody {
    private String email;
    private String password;

    public AuthenticationBody() {}
    public AuthenticationBody(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
