package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.parking.ParkingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingCheckResponse {

    private ParkingStatus checkResult;

    private ParkingDetailsDTO parkingDetails;
}
