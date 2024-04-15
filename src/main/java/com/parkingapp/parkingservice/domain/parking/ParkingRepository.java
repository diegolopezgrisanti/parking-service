package com.parkingapp.parkingservice.domain.parking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository {
    void saveParking(Parking parking);

    List<Parking> getTodayParkingsByPlateAndZone(String plate, UUID parkingZoneId);

    Optional<Parking> getParkingById(UUID id);
}
