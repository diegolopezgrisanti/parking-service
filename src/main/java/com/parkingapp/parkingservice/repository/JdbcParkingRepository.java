package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.Parking;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

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
                .addValue("parkingZoneId", parking.getParkingZoneId())
                .addValue("plate", parking.getPlate())
                .addValue("email", parking.getEmail())
                .addValue("expiration", Date.from(parking.getExpiration()));

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO parking(id, parking_zone_id, plate, email, expiration)
                VALUES (:id, :parkingZoneId, :plate, :email, :expiration)
                """,
                params
        );
    }

    @Override
    public List<Parking> getTodayParkingsByPlateAndZone(String plate, UUID parkingZoneId) {
        LocalDate today = LocalDate.now();
        LocalDateTime atBeginningOfToday = LocalDateTime.of(
                today.getYear(),
                today.getMonth(),
                today.getDayOfMonth(),
                0,
                0,
                0
        );

        Map<String, Object> params = new HashMap<>();
        params.put("plate", plate);
        params.put("parkingZoneId", parkingZoneId);
        params.put("atBeginningOfToday", Timestamp.valueOf(atBeginningOfToday));

        return namedParameterJdbcTemplate.query(
                """
                  SELECT * FROM parking 
                  WHERE plate = :plate 
                  AND parking_zone_id = :parkingZoneId
                  AND expiration > :atBeginningOfToday
               """,
                params,
                new ParkingRowMapper()
        );
    }

    @Override
    public Parking getParkingById(UUID id) {
        return namedParameterJdbcTemplate.query(
                """
                   SELECT * FROM parking
                   WHERE id = :id
                """,
                params,
                new ParkingRowMapper()
        );
    }

    private class ParkingRowMapper implements RowMapper<Parking> {

        @Override
        public Parking mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Parking(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("parking_zone_id")),
                    rs.getString("plate"),
                    rs.getString("email"),
                    rs.getTimestamp("expiration").toLocalDateTime().toInstant(ZoneOffset.UTC)
            );
        }
    }
}
