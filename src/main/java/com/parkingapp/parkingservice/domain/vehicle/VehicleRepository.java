package com.parkingapp.parkingservice.domain.vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository {
    boolean saveVehicle(Vehicle vehicle);
    List<Vehicle> getUserVehicles(UUID userId);
    boolean isVehicleIdValid(UUID vehicleId, UUID userId);
}
