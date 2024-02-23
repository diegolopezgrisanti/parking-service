package com.parkingapp.parkingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "cities")
public class CityEntity {

    @Id
    private UUID id;
    private String name;

    @OneToMany
    private List<ParkingZonesEntity> parkingZones;

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

    public List<ParkingZonesEntity> getParkingZones() {
        return parkingZones;
    }

    public void setParkingZones(List<ParkingZonesEntity> parkingZones) {
        this.parkingZones = parkingZones;
    }
}
