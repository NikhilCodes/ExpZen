package com.nikhilcodes.creditzen.shared.dto;

import lombok.Data;

@Data
public class ServerTestResponse {
    private String response;

    public ServerTestResponse(String response) {
        this.response = response;
    }
}
