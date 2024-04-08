package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.model.ParkingStatusCheck;

import java.util.UUID;

public interface ParkingService {
    Parking createParking(Parking parking);

    ParkingStatusCheck getParkingStatusCheck(String plate, UUID parkingZoneId);

    Parking getParkingById(UUID id);
}
