package com.parkingapp.parkingservice.application.getparkingbyid;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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
        Parking parking = new Parking(
                parkingId,
                UUID.randomUUID(),
                "4316KNN",
                "mock@test.com",
                Instant.now()

        );
        when(parkingRepository.getParkingById(parkingId)).thenReturn(Optional.of(parking));

        // WHEN
        Optional<Parking> result = useCase.execute(parkingId);

        // THEN
        assertThat(result).isPresent().contains(parking);

        verify(parkingRepository, times(1)).getParkingById(parkingId);
    }
}
