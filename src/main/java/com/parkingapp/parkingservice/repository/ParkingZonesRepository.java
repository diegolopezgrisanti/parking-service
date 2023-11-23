package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.ParkingZone;

import java.util.List;
import java.util.UUID;

public interface ParkingzonesRepository {
    List<ParkingZone> getParkingZoneById(UUID cityId);
}
