package com.parkingapp.parkingservice.domain.vehicle;

import com.parkingapp.parkingservice.domain.common.Country;
import lombok.Data;

import java.util.UUID;

@Data
public class Vehicle {
    private UUID id;
    private String brand;
    private String model;
    private Color color;
    private String plate;
    private Country country;
    private UUID userId;

    public Vehicle(UUID id, String brand, String model, Color color, String plate, Country country, UUID userId) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plate = plate.toUpperCase();
        this.country = country;
        this.userId = userId;
    }

    public void setPlate(String plate) {
        this.plate = plate.toUpperCase();
    }
}
