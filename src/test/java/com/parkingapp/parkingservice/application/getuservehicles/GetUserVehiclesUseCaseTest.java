package com.parkingapp.parkingservice.application.getuservehicles;

import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.vehicle.Color;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetUserVehiclesUseCaseTest {

    private final VehicleRepository vehicleRepository =  mock(VehicleRepository.class);
    private final GetUserVehiclesUseCase useCase = new GetUserVehiclesUseCase(vehicleRepository);

    @Test
    void shouldGetTheUserVehicles() {
        // GIVEN
        UUID userId = UUID.randomUUID();
        Vehicle expectedVehicle = new Vehicle(
                UUID.randomUUID(),
                "FORD",
                "FOCUS",
                Color.BLUE,
                "1234AAA",
                Country.ESP,
                userId
        );
        when(vehicleRepository.getUserVehicles(userId)).thenReturn(List.of(expectedVehicle));

        // WHEN
        List<Vehicle> result = useCase.execute(userId);

        // THEN
        assertThat(result).contains(expectedVehicle);
    }

    @Test
    void shouldNotGetVehiclesWhenUserHasNone() {
        // GIVEN
        UUID userId = UUID.randomUUID();
        when(vehicleRepository.getUserVehicles(userId)).thenReturn(List.of());

        // WHEN
        List<Vehicle> result = useCase.execute(userId);

        // THEN
        assertThat(result).isEmpty();
    }

}