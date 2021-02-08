package com.nikhilcodes.creditzen.exceptions;

import lombok.Data;

@Data
public class InvalidJwtException {
    private int code;
    private String message;
    private String type = "INVALID_JWT_EXCEPTION";

    public InvalidJwtException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
