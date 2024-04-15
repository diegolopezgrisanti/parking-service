package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JdbcParkingRepository implements ParkingRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcParkingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveParking(Parking parking) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", parking.getId())
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
    public Optional<Parking> getParkingById(UUID id) {
        return namedParameterJdbcTemplate.query(
                """
                   SELECT * FROM parking
                   WHERE id = :id
                """,
                Map.of("id", id),
                new ParkingRowMapper()
        )
                .stream().findFirst();
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
