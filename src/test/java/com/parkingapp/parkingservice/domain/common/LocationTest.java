package com.parkingapp.parkingservice.domain.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

class LocationTest {

    BigDecimal validLatitude = new BigDecimal("45.0");
    BigDecimal validLongitude = new BigDecimal("90.0");

    @Test
    void shouldCreateLocation() {
        Location newLocation = new Location(validLatitude, validLongitude);

        assert(newLocation.getLatitude()).equals(validLatitude);
        assert (newLocation.getLongitude()).equals(validLongitude);
    }

    @Test
    void shouldThrowExceptionIfLatitudeIsBelowMinimum() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(new BigDecimal("-100.0"), validLongitude);
        }, "Latitude must be between -90.0 and 90.0 degrees.");
    }

    @Test
    void shouldThrowExceptionIfLatitudeIsAboveMinimum() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(new BigDecimal("100.0"), validLongitude);
        }, "Latitude must be between -90.0 and 90.0 degrees.");
    }

    @Test
    void shouldThrowExceptionIfLatitudeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(null, validLongitude);
        }, "Latitude must be between -90.0 and 90.0 degrees.");
    }

    @Test
    void shouldThrowExceptionIfLongitudeIsBelowMinimum() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(validLatitude, new BigDecimal("-190.0"));
        }, "Longitude must be between -180.0 and 180.0 degrees.");
    }
    @Test
    void shouldThrowExceptionIfLongitudeIsAboveMinimum() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(validLatitude, new BigDecimal("181.0"));
        }, "Longitude must be between -180.0 and 180.0 degrees.");
    }

    @Test
    void shouldThrowExceptionIfLongitudeIsNull() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Location(validLatitude, null);
            }, "Longitude must be between -180.0 and 180.0 degrees.");
    }

}