package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public void createParking(Parking parking) {
        parkingRepository.saveParking(parking);
    }
}