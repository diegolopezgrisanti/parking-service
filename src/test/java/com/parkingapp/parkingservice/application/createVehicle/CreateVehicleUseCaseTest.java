package com.parkingapp.parkingservice.application.createVehicle;

import com.parkingapp.parkingservice.domain.exceptions.VehicleAlreadyExistsException;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CreateVehicleUseCaseTest {
    private final VehicleRepository vehicleRepository = mock(VehicleRepository.class);
    private final CreateVehicleUseCase useCase = new CreateVehicleUseCase(vehicleRepository);

    private final UUID vehicleId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String model = "model";
    private final String brand = "brand";
    private final String color = "blue";
    private final String plate = "4632TFR";
    private final String country = "ES";

    private final Vehicle vehicle = new Vehicle(
           vehicleId,
           brand,
           model,
           color,
           plate,
           country,
           userId
    );

    @Test
    void shouldCreateANewVehicle() {
        // GIVEN
        when(vehicleRepository.saveVehicle(vehicle)).thenReturn(true);

        // WHEN
        Vehicle result = useCase.execute(vehicle);

        // THEN
        verify(vehicleRepository).saveVehicle(vehicle);
        assertThat(result).isEqualTo(vehicle);
    }

    @Test
    void shouldNotSaveAVehicleWhenPlateAndUserIdExists() {
        // GIVEN
        when(vehicleRepository.saveVehicle(vehicle)).thenReturn(false);

        // WHEN & THEN
        assertThrows(VehicleAlreadyExistsException.class, () -> useCase.execute(vehicle));
    }
}