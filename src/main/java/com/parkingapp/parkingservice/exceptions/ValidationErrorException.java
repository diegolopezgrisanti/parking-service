package com.parkingapp.parkingservice.exceptions;

import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error.ValidationError;

import java.util.List;

public class ValidationErrorException extends ApiException {

    private List<ValidationError> validationErrors;

    public ValidationErrorException(List<ValidationError> validationErrors) {
        super("Validation errors found", 400);
        this.validationErrors = validationErrors;
    }

}
