package com.parkingapp.parkingservice.model;

import java.time.Instant;
import java.util.UUID;

public class Parking {
    private UUID id;
    private UUID cityId;
    private UUID parkingZoneId;
    private String plate;
    private String email;
    private Instant expiration;
    public Parking(UUID id, UUID cityId, UUID parkingZoneId, String plate, String email, Instant expiration) {
        this.id = id;
        this.cityId = cityId;
        this.parkingZoneId = parkingZoneId;
        this.plate = plate;
        this.email = email;
        this.expiration = expiration;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCityId() {
        return cityId;
    }

    public UUID getParkingZoneId() {
        return parkingZoneId;
    }

    public String getPlate() {
        return plate;
    }

    public String getEmail() {
        return email;
    }

    public Instant getExpiration() {
        return expiration;
    }

}