package com.parkingapp.parkingservice.domain.parkingzone;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ParkingZone {
    private UUID id;
    private String name;
    private UUID cityId;
}
