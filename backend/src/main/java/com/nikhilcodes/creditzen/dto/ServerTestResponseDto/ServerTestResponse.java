package com.nikhilcodes.creditzen.dto.ServerTestResponseDto;

import lombok.Data;

@Data
public class ServerTestResponse {
    private String response;

    public ServerTestResponse(String response) {
        this.response = response;
    }
}
