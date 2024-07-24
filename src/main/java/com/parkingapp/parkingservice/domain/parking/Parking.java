package com.parkingapp.parkingservice.domain.parking;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Parking {
    private UUID id;
    private UUID parkingZoneId;
    private UUID userId;
    private UUID vehicleId;
    private UUID paymentMethodId;
    private String plate;
    private Instant startDate;
    private Instant endDate;
    private PaymentStatus paymentStatus;

    public Parking(UUID id, UUID parkingZoneId, UUID userId, UUID vehicleId, UUID paymentMethodId, String plate, Instant startDate, Instant endDate, PaymentStatus paymentStatus) {
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Parking end date should be after start date");
        }
        this.id = id;
        this.parkingZoneId = parkingZoneId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.paymentMethodId = paymentMethodId;
        this.plate = plate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = paymentStatus;
    }
}
