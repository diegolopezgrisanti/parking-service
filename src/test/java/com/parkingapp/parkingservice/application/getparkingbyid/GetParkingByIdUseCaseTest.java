package com.parkingapp.parkingservice.application.getparkingbyid;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetParkingByIdUseCaseTest {

    private ParkingRepository parkingRepository = mock(ParkingRepository.class);
    private GetParkingByIdUseCase useCase = new GetParkingByIdUseCase(parkingRepository);

    @Test
    void shouldReturnParkingById() {
        // GIVEN
        UUID parkingId = UUID.randomUUID();
        UUID parkingZoneId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        String plate = "4616KUY";
        UUID paymentMethodId = UUID.randomUUID();
        Parking parking = new Parking(
                parkingId,
                parkingZoneId,
                userId,
                vehicleId,
                paymentMethodId,
                plate,
                Instant.now(),
                Instant.now().plus(1, ChronoUnit.HOURS),
                PaymentStatus.PENDING
        );
        when(parkingRepository.getParkingById(parkingId)).thenReturn(Optional.of(parking));

        // WHEN
        Optional<Parking> result = useCase.execute(parkingId);

        // THEN
        assertThat(result).isPresent().contains(parking);

        verify(parkingRepository, times(1)).getParkingById(parkingId);
    }
}
