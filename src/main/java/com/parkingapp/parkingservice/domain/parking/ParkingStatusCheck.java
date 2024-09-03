package com.parkingapp.parkingservice.domain.parking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingStatusCheck {

    private ParkingStatus parkingStatus;
    private Parking parking;

}
