package com.parkingapp.parkingservice.application.getparkingzones;

import com.parkingapp.parkingservice.domain.common.Location;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetParkingZonesByIdUseCaseTest {

    private final ParkingZonesRepository parkingZonesRepository = mock(ParkingZonesRepository.class);
    private final GetParkingZonesByIdUseCase useCase = new GetParkingZonesByIdUseCase(parkingZonesRepository);

    @Test
    void shouldGetParkingZonesById() {
        // GIVEN
        UUID cityId = UUID.randomUUID();
        ParkingZone parkingZone = new ParkingZone(
                UUID.randomUUID(),
                "Test zone",
                UUID.randomUUID(),
                new Location(new BigDecimal("40.7128"), new BigDecimal("-74.0060")),
                Monetary.getCurrency("EUR"),
                100
        );
        List<ParkingZone> expectedParkingZones = List.of(parkingZone);
        when(parkingZonesRepository.getParkingZonesByCityId(cityId)).thenReturn(expectedParkingZones);

        // WHEN
        List<ParkingZone> result = useCase.execute(cityId);

        // THEN
        assertThat(result).isEqualTo(expectedParkingZones);
        verify(parkingZonesRepository).getParkingZonesByCityId(cityId);
    }

}