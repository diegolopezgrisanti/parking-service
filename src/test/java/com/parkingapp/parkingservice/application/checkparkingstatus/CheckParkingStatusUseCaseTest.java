package com.parkingapp.parkingservice.application.checkparkingstatus;

import com.parkingapp.parkingservice.domain.parking.*;
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
    private final UUID parkingId = UUID.randomUUID();
    private final UUID parkingZoneId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID vehicleId = UUID.randomUUID();
    private final String plate = "4616KUY";
    private final UUID paymentMethodId = UUID.randomUUID();
    private final Instant now = Instant.now();
    private final Instant nowPlusOneHour = Instant.now().plus(1, ChronoUnit.HOURS);
    private final Instant nowMinusOneHour = Instant.now().minus(1, ChronoUnit.HOURS);
    private final PaymentStatus paymentStatusPending = PaymentStatus.PENDING;

    Parking parking = new Parking(
            parkingId,
            parkingZoneId,
            userId,
            vehicleId,
            paymentMethodId,
            now,
            nowPlusOneHour,
            paymentStatusPending
    );


    @Test
    void shouldResolveActiveStatusWhenParkingIsActive() {
        // GIVEN
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
        Parking parking = new Parking(
                parkingId,
                parkingZoneId,
                userId,
                vehicleId,
                paymentMethodId,
                now.minus(3, ChronoUnit.HOURS),
                nowMinusOneHour,
                paymentStatusPending
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
    void shouldGetParkingStatusSearchByPlateInUppercase() {
        // GIVEN
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId)).thenReturn(List.of(parking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.ACTIVE);
        assertThat(result.getParking()).isNotNull();
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

    @Test
    void shouldGetParkingStatusSearchByPlateInLowercase() {
        // GIVEN
        String plateLowercase = "4616kuy";
        when(parkingRepository.getTodayParkingsByPlateAndZone(plateLowercase, parkingZoneId)).thenReturn(List.of(parking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plateLowercase, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.ACTIVE);
        assertThat(result.getParking()).isNotNull();
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plateLowercase, parkingZoneId);
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
        Parking oldParking = new Parking(
                parkingId,
                parkingZoneId,
                userId,
                vehicleId,
                paymentMethodId,
                now.minus(3, ChronoUnit.HOURS),
                nowMinusOneHour,
                paymentStatusPending
        );
        when(parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId))
                .thenReturn(List.of(oldParking, parking));

        // WHEN
        ParkingStatusCheck result = useCase.execute(plate, parkingZoneId);

        // THEN
        assertThat(result.getParkingStatus()).isEqualTo(ParkingStatus.ACTIVE);
        assertThat(result.getParking()).isEqualTo(parking);
        verify(parkingRepository).getTodayParkingsByPlateAndZone(plate, parkingZoneId);
    }

}
