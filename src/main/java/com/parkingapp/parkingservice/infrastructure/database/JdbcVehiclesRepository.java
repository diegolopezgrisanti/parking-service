package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.exceptions.VehicleAlreadyExistsException;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcVehiclesRepository implements VehicleRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVehiclesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", vehicle.getId())
                .addValue("brand", vehicle.getBrand())
                .addValue("model", vehicle.getModel())
                .addValue("color", vehicle.getColor())
                .addValue("plate", vehicle.getPlate())
                .addValue("country", vehicle.getCountry())
                .addValue("userId", vehicle.getUserId());

        try {
            namedParameterJdbcTemplate.update(
                    """
                    INSERT INTO vehicles(id, brand, model, color, plate, country, user_id)
                    VALUES (:id, :brand, :model, :color, :plate, :country, :userId)
                    """,
                    params
            );
        } catch (DuplicateKeyException e) {
            throw new VehicleAlreadyExistsException("The combination of vehicle_id and user_id already exists.");
        }
    }
}
