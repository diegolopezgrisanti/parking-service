package com.parkingapp.parkingservice.exceptions;

public class ApiException extends RuntimeException {

    private int status;

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }
}
