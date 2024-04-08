package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;

import java.util.List;
import java.util.UUID;

public interface ParkingZonesService {
    List<ParkingZone> getAllByCityId(UUID cityId);
}
