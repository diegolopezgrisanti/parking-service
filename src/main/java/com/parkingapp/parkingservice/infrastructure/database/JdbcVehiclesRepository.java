package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.Color;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JdbcVehiclesRepository implements VehicleRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVehiclesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean saveVehicle(Vehicle vehicle) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", vehicle.getId())
                .addValue("brand", vehicle.getBrand())
                .addValue("model", vehicle.getModel())
                .addValue("color", vehicle.getColor().name())
                .addValue("plate", vehicle.getPlate())
                .addValue("country", vehicle.getCountry().name())
                .addValue("userId", vehicle.getUserId());

        return namedParameterJdbcTemplate.update(
                """
                INSERT INTO vehicles(id, brand, model, color, plate, country, user_id)
                VALUES (:id, :brand, :model, :color, :plate, :country, :userId)
                ON CONFLICT(plate, user_id) DO NOTHING;
                """,
                params
        ) > 0;
    }

    @Override
    public List<Vehicle> getUserVehicles(UUID userId) {
        String sql = """
                SELECT * FROM vehicles
                WHERE user_id = :userId
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        RowMapper<Vehicle> rowMapper = getVehicleRowMapper();

        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public boolean isVehicleIdValid(UUID vehicleId, UUID userId) {
        Integer count = namedParameterJdbcTemplate.queryForObject(
                """
                        SELECT count(*) FROM vehicles
                        WHERE id = :vehicleId
                        AND user_id = :userId
                    """,
                    Map.of("vehicleId", vehicleId, "userId", userId),
                    Integer.class);

        return count != null && count == 1;
    }

    private static @NotNull RowMapper<Vehicle> getVehicleRowMapper() {
        return (rs, rowNum) -> new Vehicle(
                UUID.fromString(rs.getString("id")),
                rs.getString("brand"),
                rs.getString("model"),
                Color.valueOf(rs.getString("color")),
                rs.getString("plate"),
                Country.valueOf(rs.getString("country")),
                UUID.fromString(rs.getString("user_id"))
        );
    }
}
