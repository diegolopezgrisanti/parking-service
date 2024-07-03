package com.parkingapp.parkingservice.domain.parking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Parking {
    private UUID id;
    private UUID parkingZoneId;
    private String plate;
    private String email;
    private Instant expiration;
}
