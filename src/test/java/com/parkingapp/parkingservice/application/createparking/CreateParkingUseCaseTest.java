package com.parkingapp.parkingservice.application.createparking;

import com.parkingapp.parkingservice.domain.exceptions.ParkingZoneNotFoundException;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateParkingUseCaseTest {

    private ParkingRepository parkingRepository = mock(ParkingRepository.class);
    private ParkingZonesRepository parkingZonesRepository = mock(ParkingZonesRepository.class);
    private CreateParkingUseCase useCase = new CreateParkingUseCase(
            parkingRepository,
            parkingZonesRepository
    );

    private UUID parkingId = UUID.randomUUID();
    private UUID parkingZoneId = UUID.randomUUID();
    private Parking parking = new Parking(
            parkingId,
            parkingZoneId,
            "ABC123",
            "dummy@email.com",
            Instant.now()
    );

    @Test
    void shouldCreateParkingWhenParkingZoneIsValid() {
        // GIVEN
        when(parkingZonesRepository.isParkingZoneIdValid(parkingZoneId)).thenReturn(true);
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

        // WHEN AND THEN
        assertThrows(ParkingZoneNotFoundException.class, () -> useCase.execute(parking));
        verify(parkingZonesRepository).isParkingZoneIdValid(parkingZoneId);
        verifyNoInteractions(parkingRepository);
    }
}
