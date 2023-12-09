package com.parkingapp.parkingservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "cities")
public class CityEntity {

    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "id")
    private ParkingZonesEntity parkingZones;

    public CityEntity() {
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

    public ParkingZonesEntity getParkingZones() {
        return parkingZones;
    }

    public void setParkingZones(ParkingZonesEntity parkingZones) {
        this.parkingZones = parkingZones;
    }
}

