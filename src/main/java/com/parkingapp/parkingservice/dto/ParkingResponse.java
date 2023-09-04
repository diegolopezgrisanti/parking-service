package com.parkingapp.parkingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ParkingResponse {

    @Schema(
            description = "List of parking"
    )
    private List<ParkingDTO> parking;

    public ParkingResponse(List<ParkingDTO> parking) {
        this.parking = parking;
    }

}
