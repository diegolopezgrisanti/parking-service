package com.parkingapp.parkingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ParkingZonesResponse {

    @Schema(
            description = "List of parking zones"
    )
    @JsonProperty("parking_zones")
    private List<ParkingZoneDTO> parkingZones;

    public ParkingZonesResponse(List<ParkingZoneDTO> parkingZones) {
        this.parkingZones = parkingZones;
    }

}
