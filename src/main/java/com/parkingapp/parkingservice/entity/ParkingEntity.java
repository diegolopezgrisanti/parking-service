package com.parkingapp.parkingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "parkings")
public class ParkingEntity {
    @Id
    private UUID id;
    private UUID city_id;
    private UUID parking_zone_id;
    private String plate;
    private String email;
    private Instant expiration;

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

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
