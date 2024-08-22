package com.parkingapp.parkingservice.infrastructure.database;


import com.parkingapp.parkingservice.domain.common.Amount;
import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosure;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.vehicle.Color;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.money.Monetary;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@WithPostgreSql
class JdbcParkingClosureRepositoryIntegrationTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ParkingClosureRepository parkingClosureRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    private final UUID vehicleId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID parkingZoneId = UUID.randomUUID();
    private final String model = "model";
    private final String brand = "brand";
    private final Color color = Color.BLUE;
    private final String plate = "4632TFR";
    private final Country country = Country.ESP;
    private final int batchSize = 1;
    private final Instant endDateUntil = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private final Instant parkingStartDate = endDateUntil.minus(45, ChronoUnit.MINUTES);
    private final Instant parkingEndDate = endDateUntil.minus(1, ChronoUnit.MINUTES);
    private final int parkingZoneFeePerMinute = 10;
    private final PaymentStatus paymentStatusPending = PaymentStatus.PENDING;
    private final UUID paymentMethodId = UUID.randomUUID();
    private final UUID parkingId = UUID.randomUUID();

    private final Vehicle newVehicle = new Vehicle(
            vehicleId,
            brand,
            model,
            color,
            plate,
            country,
            userId
    );

    private final ParkingClosure parkingClosure = new ParkingClosure(
            new Amount(Monetary.getCurrency("EUR"), parkingZoneFeePerMinute),
            parkingStartDate,
            parkingEndDate,
            parkingId,
            userId,
            paymentMethodId
    );

    private final Parking endedParking = new Parking(
            parkingId,
            parkingZoneId,
            userId,
            vehicleId,
            paymentMethodId,
            parkingStartDate,
            parkingEndDate,
            paymentStatusPending
    );

    @BeforeEach
    void setUp() {
        JdbcTestUtils.deleteFromTables(
                namedParameterJdbcTemplate.getJdbcTemplate(),
                "parking"
        );
        givenExitingParkingZone(parkingZoneId);
        givenExistingVehicle(newVehicle);
    }

    @Test
    void shouldGetParkingsWithPendingPaymentAndEndDateBeforeNow() {
        // GIVEN
        givenExistingParking(endedParking);

        // WHEN
        List<ParkingClosure> result = parkingClosureRepository.getParkingsWithPendingPayment(batchSize, endDateUntil);

        // THEN
        assertThat(result).isEqualTo(List.of(parkingClosure));
    }

    @Test
    void shouldNotGetParkingsWithPendingPaymentAndEndDateAfterNow() {
        // GIVEN
        Parking activeParking = new Parking(
                parkingId,
                parkingZoneId,
                userId,
                vehicleId,
                paymentMethodId,
                parkingStartDate,
                endDateUntil.plus(60, ChronoUnit.MINUTES),
                paymentStatusPending
        );
        givenExistingParking(activeParking);

        // WHEN
        List<ParkingClosure> result = parkingClosureRepository.getParkingsWithPendingPayment(batchSize, endDateUntil);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldNotGetParkingsWithProcessedPaymentAndEndDateBeforeNow() {
        // GIVEN
        Parking processedParking = new Parking(
                parkingId,
                parkingZoneId,
                userId,
                vehicleId,
                paymentMethodId,
                parkingStartDate,
                parkingEndDate,
                PaymentStatus.PROCESSED
        );
        givenExistingParking(processedParking);

        // WHEN
        List<ParkingClosure> result = parkingClosureRepository.getParkingsWithPendingPayment(batchSize, endDateUntil);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void shouldMarkAParkingAsProcessed() {
        // GIVEN
        givenExistingParking(endedParking);

        // WHEN
       parkingClosureRepository.markAsProcessed(endedParking.getId(), endDateUntil);
       Optional<Parking> result = parkingRepository.getParkingById(endedParking.getId());

        // THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPaymentStatus()).isEqualTo(PaymentStatus.PROCESSED);
    }

    @Test
    void shouldMarkAParkingAsFailed() {
        // GIVEN
        givenExistingParking(endedParking);

        // WHEN
        parkingClosureRepository.markAsFailed(endedParking.getId(), endDateUntil);
        Optional<Parking> result = parkingRepository.getParkingById(endedParking.getId());

        // THEN
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
    }


    private void givenExitingParkingZone(UUID parkingZoneId) {
        UUID cityId = UUID.randomUUID();
        MapSqlParameterSource cityParams = new MapSqlParameterSource()
                .addValue("id", cityId)
                .addValue("name", "dummy");

        namedParameterJdbcTemplate.update(
                """
                INSERT INTO cities(id, name)
                VALUES (:id, :name)
                """,
                cityParams
        );

        MapSqlParameterSource parkingZoneParams = new MapSqlParameterSource()
                .addValue("id", parkingZoneId)
                .addValue("name", "dummy")
                .addValue("cityId", cityId)
                .addValue("latitude", 41.79788)
                .addValue("longitude", 3.05944)
                .addValue("currency", "EUR")
                .addValue("feePerMinute", parkingZoneFeePerMinute);

        namedParameterJdbcTemplate.update(
                "INSERT INTO parking_zones(id, name, city_id, latitude, longitude, currency, fee_per_minute) " +
                        "VALUES (:id, :name, :cityId, :latitude, :longitude, :currency, :feePerMinute);",
                parkingZoneParams
        );
    }

    private void givenExistingVehicle(Vehicle vehicle) {
        vehicleRepository.saveVehicle(vehicle);
    }

    private void givenExistingParking(Parking parking) {
        parkingRepository.saveParking(parking);
    }

}