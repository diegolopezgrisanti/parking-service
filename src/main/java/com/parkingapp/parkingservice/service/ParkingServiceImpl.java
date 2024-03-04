package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.exceptions.ParkingZoneNotFoundException;
import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.repository.ParkingRepository;
import com.parkingapp.parkingservice.repository.ParkingZonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    private final ParkingZonesRepository parkingZonesRepository;

    @Autowired
    public ParkingServiceImpl(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository
    ) {
        this.parkingRepository = parkingRepository;
        this.parkingZonesRepository = parkingZonesRepository;
    }

    public Parking createParking(Parking parking) {
        if (this.isParkingZoneNonExistent(parking.getParkingZoneId())) {
            throw new ParkingZoneNotFoundException(parking.getParkingZoneId());
        }

        parkingRepository.saveParking(parking);
        return parking;
    }

    private boolean isParkingZoneNonExistent(UUID parkingZoneId) {
        return !this.parkingZonesRepository.isParkingZoneIdValid(parkingZoneId);
    }
}
