package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@WithPostgreSql
public class JdbcParkingRepositoryIntegrationTest {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void shouldSaveParking() {
        // GIVEN
        UUID parkingId = UUID.randomUUID();
        UUID parkingZoneId = UUID.randomUUID();
        Parking expectedParking = new Parking(
                parkingId,
                parkingZoneId,
                "ABC123",
                "dummy@email.com",
                Instant.now().truncatedTo(ChronoUnit.MILLIS)
        );

        givenExitingParkingZone(parkingZoneId);

        // WHEN
        parkingRepository.saveParking(expectedParking);

        // THEN
        Optional<Parking> result = parkingRepository.getParkingById(parkingId);
        assertThat(result).isEqualTo(Optional.of(expectedParking));
    }

    private void givenExitingParkingZone(UUID parkingZoneId) {
        UUID cityId = UUID.randomUUID();
        MapSqlParameterSource cityParams = new MapSqlParameterSource()
                .addValue("id", cityId)
                .addValue("name", "dummy");

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO cities(id, name)
                VALUES (:id, :name)
                """,
                cityParams
        );
        MapSqlParameterSource parkingZoneParams = new MapSqlParameterSource()
                .addValue("id", parkingZoneId)
                .addValue("name", "dummy")
                .addValue("cityId", cityId);

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO parking_zones (id, name, city_id)
                VALUES (:id, :name, :cityId)
                """,
                parkingZoneParams
        );
    }
}