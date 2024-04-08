package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import java.time.Instant;

public class ParkingDetailsDTO {

    private String plate;

    private Instant expirationDate;

    public ParkingDetailsDTO(String plate, Instant expirationDate) {
        this.plate = plate;
        this.expirationDate = expirationDate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }
}
