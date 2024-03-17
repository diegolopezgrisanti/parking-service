package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.error.ErrorResponse;
import com.parkingapp.parkingservice.dto.error.ValidationError;
import com.parkingapp.parkingservice.dto.error.ValidationErrorResponse;
import com.parkingapp.parkingservice.exceptions.ApiException;
import com.parkingapp.parkingservice.exceptions.ParkingZoneNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ApiException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handle(ApiException exception) {
        return new ErrorResponse("Internal server error, please try later");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    ErrorResponse handle(ParkingZoneNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ValidationError> errors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            ValidationError validationError = new ValidationError(fieldName, errorMessage);
            errors.add(validationError);
        });

        return new ValidationErrorResponse(errors);
    }



}
