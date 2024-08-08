package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.Color;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


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
    private final Color color = Color.BLUE;
    private final String plate = "4632TFR";
    private final Country country = Country.ESP;

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
        boolean saveResult = vehicleRepository.saveVehicle(newVehicle);

        // THEN
        List<Vehicle> result = vehicleRepository.getUserVehicles(newVehicle.getUserId());
        assertThat(result).isEqualTo(List.of(newVehicle));
        assertThat(saveResult).isTrue();
    }

    @Test
    void shouldSaveVehicleWhenVehicleWithSamePlateAndDifferentUserIdExists() {
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
        boolean saveResult = vehicleRepository.saveVehicle(newVehicle);
        boolean saveResult2 = vehicleRepository.saveVehicle(newVehiclePlateAlreadyExists);

        // THEN
        List<Vehicle> result1 = vehicleRepository.getUserVehicles(newVehicle.getUserId());
        assertThat(result1).isEqualTo(List.of(newVehicle));
        assertThat(saveResult).isTrue();

        List<Vehicle> result2 = vehicleRepository.getUserVehicles(newVehiclePlateAlreadyExists.getUserId());
        assertThat(result2).isEqualTo(List.of(newVehiclePlateAlreadyExists));
        assertThat(saveResult2).isTrue();
    }

    @Test
    void shouldSaveVehicleWhenVehicleWithSameUserIdAndDifferentPlateExists() {
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
        boolean saveResult1 = vehicleRepository.saveVehicle(newVehicle);
        boolean saveResult2 = vehicleRepository.saveVehicle(newVehicleUserIdAlreadyExists);

        // THEN
        List<Vehicle> result = vehicleRepository.getUserVehicles(newVehicle.getUserId());
        assertThat(result).containsExactlyInAnyOrder(newVehicle, newVehicleUserIdAlreadyExists);
        assertThat(saveResult1).isTrue();
        assertThat(saveResult2).isTrue();
    }

    @Test
    void shouldNotSaveAVehicleWhenItAlreadyExists() {
        // GIVEN
        Vehicle duplicateVehicle = new Vehicle(
                UUID.randomUUID(),
                brand,
                model,
                color,
                plate,
                country,
                userId
        );

        // WHEN
        boolean saveResult = vehicleRepository.saveVehicle(newVehicle);
        boolean saveResult2 = vehicleRepository.saveVehicle(duplicateVehicle);
        List<Vehicle> userVehiclesSaved = vehicleRepository.getUserVehicles(userId);

        // THEN
        assertThat(saveResult).isTrue();
        assertThat(saveResult2).isFalse();
        assertThat(userVehiclesSaved).hasSize(1);
        Vehicle savedVehicle = userVehiclesSaved.get(0);
        assertThat(savedVehicle.getId()).isEqualTo(newVehicle.getId());
    }

    @Test
    void shouldGetUserVehicles() {
        // GIVEN
        Vehicle vehicle = new Vehicle(
                UUID.randomUUID(),
                brand,
                model,
                color,
                plate,
                country,
                userId
        );

        // WHEN
        boolean saveResult = vehicleRepository.saveVehicle(vehicle);
        List<Vehicle> userVehiclesSaved = vehicleRepository.getUserVehicles(userId);

        // THEN
        assertThat(saveResult).isTrue();
        assertThat(userVehiclesSaved).hasSize(1);
        Vehicle savedVehicle = userVehiclesSaved.get(0);
        assertThat(savedVehicle.getId()).isEqualTo(vehicle.getId());

    }

}