package com.parkingapp.parkingservice.exceptions;

import java.util.List;

public class ValidationErrorException extends ApiException {

    private List<ValidationError> validationErrors;

    public ValidationErrorException(List<ValidationError> validationErrors) {
        super("Validation errors found", 400);
        this.validationErrors = validationErrors;
    }

}
