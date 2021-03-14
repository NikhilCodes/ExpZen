package com.nikhilcodes.expzen.shared.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DueDTO {
    public DueDTO(float value, String description, Date createdOn) {
        this.value = value;
        this.description = description;
        this.createdOn = createdOn;
    }

    final float value;
    final Date createdOn;
    final String description;
}
