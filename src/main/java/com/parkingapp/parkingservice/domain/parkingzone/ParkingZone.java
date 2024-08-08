package com.parkingapp.parkingservice.domain.parkingzone;

import com.parkingapp.parkingservice.domain.common.Currency;
import com.parkingapp.parkingservice.domain.common.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ParkingZone {
    private UUID id;
    private String name;
    private UUID cityId;
    private Location location;
    private Currency currency;
    private int feePerMinute;
}
