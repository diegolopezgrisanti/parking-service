package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@WithPostgreSql
class JdbcParkingZonesRepositoryIntegrationTest {

    @Autowired
    private JdbcParkingZonesRepository jdbcParkingZonesRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void shouldGetParkingZonesByCityId() {
        // GIVEN
        UUID cityId = UUID.randomUUID();
        ParkingZone parkingZone = new ParkingZone(UUID.randomUUID(), "dummy", cityId);
        List<ParkingZone> expectedParkingZones = List.of(parkingZone);
        givenExistingParkingZone(parkingZone);

        // WHEN
        List<ParkingZone> result = jdbcParkingZonesRepository.getParkingZonesByCityId(cityId);

        // THEN
        assertThat(result).isEqualTo(expectedParkingZones);
    }

    @Test
    void shouldNotGetParkingZonesForCityIdWhenNoneIsFound() {
        // GIVEN
        UUID cityId = UUID.randomUUID();

        // WHEN
        List<ParkingZone> result = jdbcParkingZonesRepository.getParkingZonesByCityId(cityId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckAParkingZoneIdIsValid() {

    }

    @Test
    void shouldCheckAParkingZoneIdIsNotValid() {

    }

    private void givenExistingParkingZone(ParkingZone parkingZone) {
        MapSqlParameterSource cityParams = new MapSqlParameterSource()
                .addValue("id", parkingZone.getCityId())
                .addValue("name", "dummy");

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO cities(id, name)
                VALUES (:id, :name)
                """,
                cityParams
        );

        MapSqlParameterSource parkingZoneParams = new MapSqlParameterSource()
                .addValue("id", parkingZone.getId())
                .addValue("name", parkingZone.getName())
                .addValue("cityId", parkingZone.getCityId());

        namedParameterJdbcTemplate.update(
                "INSERT INTO parking_zones(id, name, city_id) VALUES (:id, :name, :cityId);",
                parkingZoneParams
        );
    }

}