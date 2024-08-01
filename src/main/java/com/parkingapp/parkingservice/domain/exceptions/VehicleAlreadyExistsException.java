package com.parkingapp.parkingservice.domain.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException() {
        super("The combination of vehicle_id and user_id already exists.");
    }
}
