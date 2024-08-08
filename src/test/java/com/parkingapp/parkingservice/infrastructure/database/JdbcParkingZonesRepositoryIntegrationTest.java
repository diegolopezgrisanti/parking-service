package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.common.Currency;
import com.parkingapp.parkingservice.domain.common.Location;
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
        ParkingZone parkingZone = new ParkingZone(
                UUID.randomUUID(),
                "Test zone",
                cityId,
                new Location(40.71288, -74.00601),
                Currency.EUR,
                100
        );
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
        // GIVEN
        UUID cityId = UUID.randomUUID();
        ParkingZone parkingZone = new ParkingZone(
                UUID.randomUUID(),
                "Test zone",
                cityId,
                new Location(40.71289, -74.00601),
                Currency.EUR,
                100
        );
        givenExistingParkingZone(parkingZone);
        // WHEN
        boolean result = jdbcParkingZonesRepository.isParkingZoneIdValid(parkingZone.getId());

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckAParkingZoneIdIsNotValid() {
        // GIVEN
        UUID parkingZoneId = UUID.randomUUID();

        // WHEN
        boolean result = jdbcParkingZonesRepository.isParkingZoneIdValid(parkingZoneId);

        // THEN
        assertThat(result).isFalse();
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

        double latitude = parkingZone.getLocation().getLatitude();
        double longitude = parkingZone.getLocation().getLongitude();

        MapSqlParameterSource parkingZoneParams = new MapSqlParameterSource()
                .addValue("id", parkingZone.getId())
                .addValue("name", parkingZone.getName())
                .addValue("cityId", parkingZone.getCityId())
                .addValue("latitude", latitude)
                .addValue("longitude", longitude)
                .addValue("currency", parkingZone.getCurrency().name())
                .addValue("feePerMinute", parkingZone.getFeePerMinute());

        namedParameterJdbcTemplate.update(
                "INSERT INTO parking_zones(id, name, city_id, latitude, longitude, currency, fee_per_minute) " +
                        "VALUES (:id, :name, :cityId, :latitude, :longitude, :currency, :feePerMinute);",
                parkingZoneParams
        );
    }

}