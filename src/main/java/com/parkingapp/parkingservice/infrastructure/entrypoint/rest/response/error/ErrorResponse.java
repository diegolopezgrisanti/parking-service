package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
