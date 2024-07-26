package com.parkingapp.parkingservice.application.createVehicle;

import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import com.parkingapp.parkingservice.domain.exceptions.VehicleAlreadyExistsException;

public class CreateVehicleUseCase {
    private final VehicleRepository vehicleRepository;

    public CreateVehicleUseCase(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle execute(Vehicle vehicle) {
        if (vehicleAlreadyExists(vehicle)) {
            throw new VehicleAlreadyExistsException("The combination of vehicle_id and user_id already exists.");
        }
        vehicleRepository.saveVehicle(vehicle);
        return vehicle;
    }

    private boolean vehicleAlreadyExists(Vehicle vehicle) {
        return this.vehicleRepository.vehicleExistsByUserIdAndPlate(vehicle);
    };
}
