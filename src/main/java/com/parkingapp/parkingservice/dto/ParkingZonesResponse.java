package com.parkingapp.parkingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ParkingZonesResponse {

    @JsonProperty("parking_zones")
    private List<ParkingZoneDTO> parkingZones;

    public ParkingZonesResponse(List<ParkingZoneDTO> parkingZones) {
        this.parkingZones = parkingZones;
    }

}
