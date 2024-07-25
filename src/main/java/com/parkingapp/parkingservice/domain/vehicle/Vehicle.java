package com.parkingapp.parkingservice.domain.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Vehicle {
    private UUID id;
    private String brand;
    private String model;
    private String color;
    private String plate;
    private String country;
    private UUID userId;
}
