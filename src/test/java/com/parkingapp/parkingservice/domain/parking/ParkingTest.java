package com.parkingapp.parkingservice.domain.parking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class ParkingTest {

    private final UUID parkingId = UUID.randomUUID();
    private final UUID parkingZoneId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID vehicleId = UUID.randomUUID();
    private final UUID paymentMethodId = UUID.randomUUID();
    private final String plate = "4616KUY";
    private final Instant now = Instant.now();

    @Test
    void shouldThrowAExceptionWhenParkingEndDateIsBeforeStartDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Parking(
                    parkingId,
                    parkingZoneId,
                    userId,
                    vehicleId,
                    paymentMethodId,
                    plate,
                    now.plus(1, ChronoUnit.HOURS),
                    now,
                    PaymentStatus.PENDING
            );
        }, "Parking end date should be after start date");
    }

    @Test
    void shouldThrowAExceptionWhenParkingEndDateAndStartDateAreEquals() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Parking(
                    parkingId,
                    parkingZoneId,
                    userId,
                    vehicleId,
                    paymentMethodId,
                    plate,
                    now,
                    now,
                    PaymentStatus.PENDING
            );
        }, "Parking end date should be after start date");
    }

    @Test
    void shouldNotThrowExceptionWhenEndDateIsAfterStartDate() {
        assertDoesNotThrow(() -> {
            new Parking(
                    parkingId,
                    parkingZoneId,
                    userId,
                    vehicleId,
                    paymentMethodId,
                    plate,
                    now,
                    now.plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.MILLIS),
                    PaymentStatus.PENDING
            );
        });
    }
}
