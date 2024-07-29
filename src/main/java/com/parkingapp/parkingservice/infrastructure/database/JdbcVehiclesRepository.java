package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Optional;
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
                .addValue("color", vehicle.getColor())
                .addValue("plate", vehicle.getPlate())
                .addValue("country", vehicle.getCountry())
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
    public boolean vehicleExistsByUserIdAndPlate(Vehicle vehicle) {
        String sql = """
                SELECT COUNT(*) FROM vehicles
                WHERE user_id = :userId AND plate = :plate
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", vehicle.getUserId())
                .addValue("plate", vehicle.getPlate());

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public Optional<Vehicle> getVehicleByUserIdAndPlate(UUID userId, String plate) {
        String sql = """
                SELECT * FROM vehicles
                WHERE user_id = :userId AND plate = :plate
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("plate", plate);

        RowMapper<Vehicle> rowMapper = (rs, rowNum) -> new Vehicle(
                UUID.fromString(rs.getString("id")),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getString("color"),
                rs.getString("plate"),
                rs.getString("country"),
                UUID.fromString(rs.getString("user_id"))
        );

        Vehicle result = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);

        return Optional.ofNullable(result);
    }
}
