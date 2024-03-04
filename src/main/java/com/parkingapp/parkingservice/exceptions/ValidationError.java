package com.parkingapp.parkingservice.exceptions;

public class ValidationError {

    private String message;
    private String field;

    public ValidationError(String message, String field) {
        this.message = message;
        this.field = field;
    }
}
