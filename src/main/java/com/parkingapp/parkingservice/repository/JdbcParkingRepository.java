package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.Parking;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public class JdbcParkingRepository implements ParkingRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcParkingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveParking(Parking parking) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID())
                .addValue("cityId", parking.getCityId())
                .addValue("parkingZoneId", parking.getParkingZoneId())
                .addValue("plate", parking.getPlate())
                .addValue("email", parking.getEmail())
                .addValue("expiration", Date.from(parking.getExpiration()));

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO parking(id, city_id, parking_zone_id, plate, email, expiration)
                VALUES (:id, :cityId, :parkingZoneId, :plate, :email, :expiration)
                """,
                params
        );
    }
}
