package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ParkingDetailsDTO {

    private String plate;

    private Instant endDate;

    public ParkingDetailsDTO(String plate, Instant endDate) {
        this.plate = plate;
        this.endDate = endDate;
    }

}
