package com.nikhilcodes.creditzen.shared.dto;

import lombok.Data;

@Data
public class ServerTestResponseDTO {
    private String response;

    public ServerTestResponseDTO(String response) {
        this.response = response;
    }
}
