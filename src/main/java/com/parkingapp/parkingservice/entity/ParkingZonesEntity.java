package com.parkingapp.parkingservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "parking_zones")
public class ParkingZonesEntity {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    private CityEntity city;

    public ParkingZonesEntity() {
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

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }
}

