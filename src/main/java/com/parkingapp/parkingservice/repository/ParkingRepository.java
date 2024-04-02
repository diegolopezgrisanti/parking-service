package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.Parking;

import java.util.List;
import java.util.UUID;

public interface ParkingRepository {
    void saveParking(Parking parking);

    List<Parking> getTodayParkingsByPlateAndZone(String plate, UUID parkingZoneId);
}
