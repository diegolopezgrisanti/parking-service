package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.ParkingZone;

import java.util.List;
import java.util.UUID;

public interface ParkingZonesService {
    List<ParkingZone> findById(UUID cityId);
}