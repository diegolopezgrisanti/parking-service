package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.Parking;

public interface ParkingRepository {
    void saveParking(Parking parking);
}
