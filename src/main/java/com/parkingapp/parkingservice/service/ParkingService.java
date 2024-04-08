package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;

import java.util.Optional;
import java.util.UUID;

public interface ParkingService {
    Parking createParking(Parking parking);

    ParkingStatusCheck getParkingStatusCheck(String plate, UUID parkingZoneId);

    Optional<Parking> getParkingById(UUID id);
}
