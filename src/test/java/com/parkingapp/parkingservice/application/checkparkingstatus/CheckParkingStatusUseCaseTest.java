package com.parkingapp.parkingservice.application.checkparkingstatus;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingStatus;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CheckParkingStatusUseCaseTest {

    private final ParkingRepository parkingRepository = mock(ParkingRepository.class);
    private final CheckParkingStatusUseCase useCase = new CheckParkingStatusUseCase(parkingRepository);
    private final String plate = "ABC123";
    private final UUID parkingZoneId = UUID.randomUUID();
    private final Instant now = Instant.now();

    @Test
    void shouldResolveActiveStatusWhenParkingIsActive() {
        // GIVEN
        Instant expirationDateInTheFuture = now.plus(1, ChronoUnit.HOURS);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                expirationDateInTheFuture
        );
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId)).thenReturn(List.of(parking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.ACTIVE);
        assertThat(result.getParking()).isEqualTo(parking);
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

    @Test
    void shouldResolveExpiredStatusWhenParkingIsExpired() {
        // GIVEN
        Instant expirationDateInThePast = now.minus(1, ChronoUnit.HOURS);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                expirationDateInThePast
        );
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId)).thenReturn(List.of(parking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.EXPIRED);
        assertThat(result.getParking()).isEqualTo(parking);
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

    @Test
    void shouldResolveNotFoundStatusWhenNoParkingFound() {
        // GIVEN
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId)).thenReturn(List.of());

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.NOT_FOUND);
        assertThat(result.getParking()).isNull();
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

    @Test
    void shouldResolveStatusWithLastParkingWhenMultipleParkingFound() {
        // GIVEN
        Instant expirationDateInTheFuture = now.plus(1, ChronoUnit.HOURS);
        Instant expirationDateInThePast = now.minus(1, ChronoUnit.HOURS);
        Parking expectedParking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                expirationDateInTheFuture
        );
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                expirationDateInThePast
        );
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId))
                .thenReturn(List.of(parking, expectedParking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.ACTIVE);
        assertThat(result.getParking()).isEqualTo(expectedParking);
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

}
