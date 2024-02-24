package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.ParkingZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.emptyMap;

@Repository
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