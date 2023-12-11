package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.ParkingZone;

import java.util.List;
import java.util.UUID;

public interface ParkingZonesRepository {
    List<ParkingZone> getParkingZonesByCityId(UUID cityId);
}
