package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.common.Location;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JdbcParkingZonesRepository implements ParkingZonesRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
    public boolean isParkingZoneIdValid(UUID parkingZoneId) {
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

            BigDecimal latitude = rs.getBigDecimal("latitude");
            BigDecimal longitude = rs.getBigDecimal("longitude");
            Location location = new Location(latitude, longitude);
            CurrencyUnit currency = Monetary.getCurrency(rs.getString("currency"));
            int feePerMinute = rs.getInt("fee_per_minute");
            Amount amount = new Amount(currency, feePerMinute);

            return new ParkingZone(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"),
                    UUID.fromString(rs.getString("city_id")),
                    location,
                    amount
            );
        }
    }
}
