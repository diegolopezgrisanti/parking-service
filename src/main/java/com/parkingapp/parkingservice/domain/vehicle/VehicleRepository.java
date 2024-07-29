package com.parkingapp.parkingservice.domain.vehicle;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository {
    boolean saveVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicleByUserIdAndPlate(UUID userId, String plate);
}
