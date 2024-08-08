package com.parkingapp.parkingservice.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
}
