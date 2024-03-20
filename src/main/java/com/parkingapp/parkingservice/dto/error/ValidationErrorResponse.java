package com.parkingapp.parkingservice.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {

    @JsonProperty("validation_errors")
    private List<ValidationError> validationErrors;

    public ValidationErrorResponse(List<ValidationError> validationErrors) {
        super("Validation errors found");
        this.validationErrors = validationErrors;
    }

}
