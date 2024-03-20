package com.parkingapp.parkingservice.exceptions;

import java.util.UUID;

public class ParkingZoneNotFoundException extends ApiException {

    public ParkingZoneNotFoundException(UUID parkingZoneId) {
        super(String.format("Parking zone with id %s not found", parkingZoneId), 400);
    }

}
