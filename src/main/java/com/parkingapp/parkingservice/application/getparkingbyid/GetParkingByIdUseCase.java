package com.parkingapp.parkingservice.application.getparkingbyid;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;

import java.util.Optional;
import java.util.UUID;

public class GetParkingByIdUseCase {

    private final ParkingRepository parkingRepository;

    public GetParkingByIdUseCase(
            ParkingRepository parkingRepository
    ) {
        this.parkingRepository = parkingRepository;
    }

    public Optional<Parking> execute(UUID id) {
        return parkingRepository.getParkingById(id);
    }
}
