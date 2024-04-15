package com.parkingapp.parkingservice.domain.exceptions;

import java.util.UUID;

public class ParkingZoneNotFoundException extends RuntimeException {

    public ParkingZoneNotFoundException(UUID parkingZoneId) {
        super(String.format("Parking zone with id %s not found", parkingZoneId));
    }

}
