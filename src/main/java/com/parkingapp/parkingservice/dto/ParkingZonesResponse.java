package com.parkingapp.parkingservice.dto;

import java.util.List;

public class ParkingZonesResponse {

    private List<ParkingZoneDTO> parking_zones;

    public ParkingZonesResponse(List<ParkingZoneDTO> parking_zones) {
        this.parking_zones = parking_zones;
    }

    public List<ParkingZoneDTO> getParkingZone() {
        return parking_zones;
    }

    public void setParkingZones(List<ParkingZoneDTO> parking_zones) {
        this.parking_zones = parking_zones;
    }

}
