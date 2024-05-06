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
    void shouldReturnAllCities() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Instant mock_instant = Instant.parse("1970-01-01T00:00:00Z");
        Parking parking = new Parking(
                id,
                UUID.randomUUID(),
                "4316KNN",
                "mock@test.com",
                mock_instant

        );
        when(parkingRepository.getParkingById(id)).thenReturn(Optional.of(parking));

        // WHEN
        Optional<Parking> result = useCase.execute(id);

        // THEN
        assertThat(result).isPresent().contains(parking);

        verify(parkingRepository, times(1)).getParkingById(id);
    }
}
