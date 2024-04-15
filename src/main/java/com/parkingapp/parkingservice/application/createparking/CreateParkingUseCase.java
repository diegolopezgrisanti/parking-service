package com.parkingapp.parkingservice.application.createparking;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.exceptions.ParkingZoneNotFoundException;

import java.util.UUID;

public class CreateParkingUseCase {

    private final ParkingRepository parkingRepository;

    private final ParkingZonesRepository parkingZonesRepository;

    public CreateParkingUseCase(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository
    ) {
        this.parkingRepository = parkingRepository;
        this.parkingZonesRepository = parkingZonesRepository;
    }

    public Parking execute(Parking parking) {
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
