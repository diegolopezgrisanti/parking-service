package com.parkingapp.parkingservice.application.createVehicle;

import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;

public class CreateVehicleUseCase {
    private final VehicleRepository vehicleRepository;

    public CreateVehicleUseCase(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle execute(Vehicle vehicle) {
        vehicleRepository.saveVehicle(vehicle);
        return vehicle;
    }
}
