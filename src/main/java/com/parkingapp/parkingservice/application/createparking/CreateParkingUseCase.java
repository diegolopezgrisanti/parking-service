package com.parkingapp.parkingservice.application.createparking;

import com.parkingapp.parkingservice.domain.exceptions.VehicleNotFoundException;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.exceptions.ParkingZoneNotFoundException;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;

import java.util.UUID;

public class CreateParkingUseCase {

    private final ParkingRepository parkingRepository;

    private final ParkingZonesRepository parkingZonesRepository;

    private final VehicleRepository vehicleRepository;

    public CreateParkingUseCase(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository,
            VehicleRepository vehicleRepository
    ) {
        this.parkingRepository = parkingRepository;
        this.parkingZonesRepository = parkingZonesRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Parking execute(Parking parking) {
        if (this.isParkingZoneNonExistent(parking.getParkingZoneId())) {
            throw new ParkingZoneNotFoundException(parking.getParkingZoneId());
        }

        if (this.isVehicleNonExistent(parking.getVehicleId(), parking.getUserId())) {
            throw new VehicleNotFoundException(parking.getVehicleId());
        }

        parkingRepository.saveParking(parking);
        return parking;
    }

    private boolean isParkingZoneNonExistent(UUID parkingZoneId) {
        return !this.parkingZonesRepository.isParkingZoneIdValid(parkingZoneId);
    }

    private boolean isVehicleNonExistent(UUID vehicleId, UUID userId) {
        return !this.vehicleRepository.isVehicleIdValid(vehicleId, userId);
    }
}
