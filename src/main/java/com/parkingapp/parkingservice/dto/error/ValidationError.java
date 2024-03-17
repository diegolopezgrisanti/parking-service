package com.parkingapp.parkingservice.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationError {

    @JsonProperty("message")
    private String message;

    @JsonProperty("field")
    private String field;

    public ValidationError(String field, String message) {
        this.message = message;
        this.field = field;
    }
}
