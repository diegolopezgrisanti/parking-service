package com.parkingapp.parkingservice.domain.city;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class City {
    private UUID id;
    private String name;
}
