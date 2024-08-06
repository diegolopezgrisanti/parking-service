package com.parkingapp.parkingservice.application.createparking;

import com.parkingapp.parkingservice.domain.exceptions.ParkingZoneNotFoundException;
import com.parkingapp.parkingservice.domain.exceptions.VehicleNotFoundException;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateParkingUseCaseTest {

    private final ParkingRepository parkingRepository = mock(ParkingRepository.class);
    private final ParkingZonesRepository parkingZonesRepository = mock(ParkingZonesRepository.class);
    private final VehicleRepository vehicleRepository = mock(VehicleRepository.class);
    private final CreateParkingUseCase useCase = new CreateParkingUseCase(
            parkingRepository,
            parkingZonesRepository,
            vehicleRepository
    );

    private final UUID parkingId = UUID.randomUUID();
    private final UUID parkingZoneId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID vehicleId = UUID.randomUUID();
    private final UUID paymentMethodId = UUID.randomUUID();
    private final Parking parking = new Parking(
            parkingId,
            parkingZoneId,
            userId,
            vehicleId,
            paymentMethodId,
            Instant.now(),
            Instant.now().plus(1, ChronoUnit.HOURS),
            PaymentStatus.PENDING
    );

    @Test
    void shouldCreateParkingWhenParkingZoneIsValid() {
        // GIVEN
        when(parkingZonesRepository.isParkingZoneIdValid(parkingZoneId)).thenReturn(true);
        when(vehicleRepository.isVehicleIdValid(vehicleId, userId)).thenReturn(true);
        doNothing().when(parkingRepository).saveParking(parking);

        // WHEN
        Parking result = useCase.execute(parking);

        // THEN
        verify(parkingZonesRepository).isParkingZoneIdValid(parkingZoneId);
        verify(parkingRepository).saveParking(parking);
        assertThat(result).isEqualTo(parking);
    }

    @Test
    void shouldNotCreateParkingWhenParkingZoneIsNotValid() {
        // GIVEN
        when(parkingZonesRepository.isParkingZoneIdValid(parkingZoneId)).thenReturn(false);
        when(vehicleRepository.isVehicleIdValid(vehicleId, userId)).thenReturn(true);

        // WHEN AND THEN
        assertThrows(ParkingZoneNotFoundException.class, () -> useCase.execute(parking));
        verify(parkingZonesRepository).isParkingZoneIdValid(parkingZoneId);
        verifyNoInteractions(parkingRepository);
    }

    @Test
    void shouldNotCreateParkingWhenVehicleIsNotValid() {
        // GIVEN
        when(parkingZonesRepository.isParkingZoneIdValid(parkingZoneId)).thenReturn(true);
        when(vehicleRepository.isVehicleIdValid(vehicleId, userId)).thenReturn(false);

        // WHEN AND THEN
        assertThrows(VehicleNotFoundException.class, () -> useCase.execute(parking));
        verify(vehicleRepository).isVehicleIdValid(vehicleId, userId);
        verifyNoInteractions(parkingRepository);
    }
}
