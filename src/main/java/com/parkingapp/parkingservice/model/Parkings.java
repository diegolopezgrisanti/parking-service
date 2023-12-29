package com.parkingapp.parkingservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Parkings {
    private UUID id;
    private UUID city_id;
    private UUID parking_zone_id;
    private String plate;
    private String email;
    private LocalDateTime expiration;

    public Parkings(UUID id, UUID city_id, UUID parking_zone_id, String plate, String email, LocalDateTime expiration) {
        this.id = id;
        this.city_id = city_id;
        this.parking_zone_id = parking_zone_id;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}