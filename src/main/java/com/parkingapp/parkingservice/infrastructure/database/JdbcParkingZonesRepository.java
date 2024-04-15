package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JdbcParkingZonesRepository implements ParkingZonesRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcParkingZonesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ParkingZone> getParkingZonesByCityId(UUID cityId) {
        return namedParameterJdbcTemplate.query(
                """
                   select * from parking_zones where city_id = :cityId
                """,
                Map.of("cityId", cityId),
                new ParkingZonesRowMapper()
        );
    }

    @Override
    public boolean isParkingZoneIdValid(java.util.UUID parkingZoneId) {
        return namedParameterJdbcTemplate.queryForObject(
                """
                   select count(*) from parking_zones where id = :parkingZoneId
                """,
                Map.of("parkingZoneId", parkingZoneId),
                Integer.class
        ) == 1;
    }

    private class ParkingZonesRowMapper implements RowMapper<ParkingZone> {
        @Override
        public ParkingZone mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ParkingZone(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name")
            );
        }
    }
}
