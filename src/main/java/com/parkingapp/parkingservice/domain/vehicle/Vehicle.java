package com.parkingapp.parkingservice.domain.vehicle;

import com.parkingapp.parkingservice.domain.common.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Vehicle {
    private UUID id;
    private String brand;
    private String model;
    private Color color;
    private String plate;
    private Country country;
    private UUID userId;
}
