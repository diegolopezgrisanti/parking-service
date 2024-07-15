package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    private String plate = "DUMMY";
    private UUID parkingZoneId = UUID.randomUUID();
    private Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(
                namedParameterJdbcTemplate.getJdbcTemplate(),
                "parking"
        );
    }

    @Test
    public void shouldSaveParking() {
        // GIVEN
        UUID parkingId = UUID.randomUUID();
        Parking expectedParking = new Parking(
                parkingId,
                parkingZoneId,
                "ABC123",
                "dummy@email.com",
                now
        );

        givenExitingParkingZone(parkingZoneId);

        // WHEN
        parkingRepository.saveParking(expectedParking);

        // THEN
        Optional<Parking> result = parkingRepository.getParkingById(parkingId);
        assertThat(result).isEqualTo(Optional.of(expectedParking));
    }

    @Test
    void shouldNotFindTodayParkingsWhenThereIsNoMatchForParkingZone() {
        // GIVEN
        UUID notMatchingParkingZoneId = UUID.randomUUID();
        givenExitingParkingZone(notMatchingParkingZoneId);
        Parking parking = new Parking(
                UUID.randomUUID(),
                notMatchingParkingZoneId,
                plate,
                "dummy@email.com",
                now
        );
        parkingRepository.saveParking(parking);

        // WHEN
        List<Parking> result = parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldNotFindTodayParkingsWhenThereIsNoMatchForPlate() {
        // GIVEN
        givenExitingParkingZone(parkingZoneId);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                "NOT_MATCH",
                "dummy@email.com",
                now
        );
        parkingRepository.saveParking(parking);

        // WHEN
        List<Parking> result = parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldNotFindParkingsThatExpiredBeforeToday() {
        // GIVEN
        givenExitingParkingZone(parkingZoneId);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                now.minus(1, ChronoUnit.DAYS)
        );
        parkingRepository.saveParking(parking);

        // WHEN
        List<Parking> result = parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindTodayParkings() {
        // GIVEN
        givenExitingParkingZone(parkingZoneId);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                now
        );
        parkingRepository.saveParking(parking);

        // WHEN
        List<Parking> result = parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);

        // THEN
        assertThat(result).contains(parking);
    }

    @Test
    void shouldFindTodayParkingsWhenThereAreMultiple() {
        // GIVEN
        givenExitingParkingZone(parkingZoneId);
        Parking parking = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                now
        );
        Parking parking2 = new Parking(
                UUID.randomUUID(),
                parkingZoneId,
                plate,
                "dummy@email.com",
                now.plus(3, ChronoUnit.HOURS)
        );
        parkingRepository.saveParking(parking);
        parkingRepository.saveParking(parking2);

        // WHEN
        List<Parking> result = parkingRepository.getTodayParkingsByPlateAndZone(plate, parkingZoneId);

        // THEN
        assertThat(result).contains(parking, parking2);
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
