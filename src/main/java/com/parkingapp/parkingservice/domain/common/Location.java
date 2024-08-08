package com.parkingapp.parkingservice.domain.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Location {
    private BigDecimal latitude;
    private BigDecimal longitude;

    private static final BigDecimal LATITUDE_MIN = new BigDecimal("-90.0");
    private static final BigDecimal LATITUDE_MAX = new BigDecimal("90.0");
    private static final BigDecimal LONGITUDE_MIN = new BigDecimal("-180.0");
    private static final BigDecimal LONGITUDE_MAX = new BigDecimal("180.0");

    public Location(BigDecimal latitude, BigDecimal longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateLatitude(BigDecimal latitude) {
        if (latitude == null || latitude.compareTo(LATITUDE_MIN) < 0 || latitude.compareTo(LATITUDE_MAX) > 0) {
            throw new IllegalArgumentException("Latitude must be between -90.0 and 90.0 degrees.");
        }
    }

    private void validateLongitude(BigDecimal longitude) {
        if (longitude == null || longitude.compareTo(LONGITUDE_MIN) < 0 || longitude.compareTo(LONGITUDE_MAX) > 0) {
            throw new IllegalArgumentException("Longitude must be between -180.0 and 180.0 degrees.");
        }
    }
}
