package com.parkingapp.parkingservice.domain.exceptions;

import java.util.UUID;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(UUID vehicleId) {
        super(String.format("Vehicle with id %s not found", vehicleId));
    }
}
