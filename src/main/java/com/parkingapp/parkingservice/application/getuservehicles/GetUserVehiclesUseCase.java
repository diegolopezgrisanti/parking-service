package com.parkingapp.parkingservice.application.getuservehicles;

import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;

import java.util.List;
import java.util.UUID;

public class GetUserVehiclesUseCase {

    private final VehicleRepository vehicleRepository;

    public GetUserVehiclesUseCase(
            VehicleRepository vehicleRepository
    ) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> execute(UUID userId) { return vehicleRepository.getUserVehicles(userId); }
}
