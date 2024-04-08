package com.parkingapp.parkingservice.domain.parkingzone;

import java.util.UUID;

public class ParkingZone {

    private UUID id;
    private String name;
    private UUID cityId;

    public ParkingZone(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCityId() {
        return cityId;
    }
    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }
}
