package com.parkingapp.parkingservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateParkingRequest {
    private String plate;
    private UUID city_id;
    private UUID parking_zone_id;

    private LocalDateTime expiration;
    private String email;

    public CreateParkingRequest(String plate, UUID city_id, UUID parking_zone_id, LocalDateTime expiration, String email) {
        this.plate = plate;
        this.city_id = city_id;
        this.parking_zone_id = parking_zone_id;
        this.expiration = expiration;
        this.email = email;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public UUID getCity_id() {
        return city_id;
    }

    public void setCity_id(UUID city_id) {
        this.city_id = city_id;
    }

    public UUID getParking_zone_id() {
        return parking_zone_id;
    }

    public void setParking_zone_id(UUID parking_zone_id) {
        this.parking_zone_id = parking_zone_id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
