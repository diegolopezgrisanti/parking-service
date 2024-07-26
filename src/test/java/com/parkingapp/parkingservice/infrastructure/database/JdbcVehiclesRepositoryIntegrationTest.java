package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


@IntegrationTest
@WithPostgreSql
public class JdbcVehiclesRepositoryIntegrationTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UUID vehicleId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String model = "model";
    private final String brand = "brand";
    private final String color = "blue";
    private final String plate = "4632TFR";
    private final String country = "ES";

    Vehicle newVehicle = new Vehicle(
            vehicleId,
            brand,
            model,
            color,
            plate,
            country,
            userId
    );

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(
                namedParameterJdbcTemplate.getJdbcTemplate(),
                "vehicles"
        );
    }

    @Test
    void shouldSaveANewVehicle() {
        // WHEN
        vehicleRepository.saveVehicle(newVehicle);

        // THEN
        Optional<Vehicle> result = vehicleRepository.getVehicleByUserIdAndPlate(newVehicle.getUserId(), newVehicle.getPlate());
        assertThat(result).isEqualTo(Optional.of(newVehicle));
    }

    @Test
    void shouldSaveANewVehicleWhenAlreadyAExistsPlateButNoUserId() {
        // GIVEN
        Vehicle newVehiclePlateAlreadyExists = new Vehicle(
                UUID.randomUUID(),
                brand,
                model,
                color,
                plate,
                country,
                UUID.randomUUID()
        );

        // WHEN
        vehicleRepository.saveVehicle(newVehicle);
        vehicleRepository.saveVehicle(newVehiclePlateAlreadyExists);

        // THEN
        Optional<Vehicle> result1 = vehicleRepository.getVehicleByUserIdAndPlate(
                newVehicle.getUserId(),
                newVehicle.getPlate()
        );
        assertThat(result1).isEqualTo(Optional.of(newVehicle));

        Optional<Vehicle> result2 = vehicleRepository.getVehicleByUserIdAndPlate(
                newVehiclePlateAlreadyExists.getUserId(),
                newVehiclePlateAlreadyExists.getPlate()
        );
        assertThat(result2).isEqualTo(Optional.of(newVehiclePlateAlreadyExists));
    }

    @Test
    void shouldSaveANewVehicleWhenAlreadyAExistsUserIdButNoPlate() {
        // GIVEN
        Vehicle newVehicleUserIdAlreadyExists = new Vehicle(
                UUID.randomUUID(),
                brand,
                model,
                color,
                "1111XXX",
                country,
                userId
        );

        // WHEN
        vehicleRepository.saveVehicle(newVehicle);
        vehicleRepository.saveVehicle(newVehicleUserIdAlreadyExists);

        // THEN
        Optional<Vehicle> result1 = vehicleRepository.getVehicleByUserIdAndPlate(
                newVehicle.getUserId(),
                newVehicle.getPlate()
        );
        assertThat(result1).isEqualTo(Optional.of(newVehicle));
        Optional<Vehicle> result2 = vehicleRepository.getVehicleByUserIdAndPlate(
                newVehicleUserIdAlreadyExists.getUserId(),
                newVehicleUserIdAlreadyExists.getPlate()
        );
        assertThat(result2).isEqualTo(Optional.of(newVehicleUserIdAlreadyExists));
    }

    @Test
    void shouldNotSaveAVehicleWhenAlreadyExistsOneWhitSamePlateAndUserId() {
        // GIVEN
        Vehicle newVehicleUserIdAndPlateAlreadyExists = new Vehicle(
                UUID.randomUUID(),
                brand,
                model,
                color,
                plate,
                country,
                userId
        );

        // WHEN
        vehicleRepository.saveVehicle(newVehicle);

        // THEN
        assertThrows(DataIntegrityViolationException.class,
                () -> vehicleRepository.saveVehicle(newVehicleUserIdAndPlateAlreadyExists));
    }

}