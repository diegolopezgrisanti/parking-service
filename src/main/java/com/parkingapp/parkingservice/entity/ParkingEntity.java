package com.parkingapp.parkingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name= "parking")
public class ParkingEntity {
    @Id
    private UUID id;
    private UUID cityId;
    private UUID parkingZoneId;
    private String plate;
    private String email;
    private Instant expiration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public UUID getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(UUID parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
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
