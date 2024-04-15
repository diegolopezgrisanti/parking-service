package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.parking.Parking;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public class ParkingResponse {

    @Schema(
            description = "Parking ID",
            example = "b95f3649-fe78-44a1-b345-5dcb318523e1"
    )
    private UUID id;

    @Schema(
            description = "Vehicle plate",
            example = "4736KTZ"
    )
    private String plate;

    @Schema(
            name = "parking_zone_id",
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    private UUID parkingZoneId;

    @Schema(
            description = "Parking expiration date time with zone",
            example = "2023-09-18T16:18:58.165+00:00"
    )
    private Instant expiration;

    @Schema(
            description = "User email",
            example = "dummy_user@mail.com"
    )
    private String email;

    public ParkingResponse(Parking parking) {
        this.id = parking.getId();
        this.plate = parking.getPlate();
        this.parkingZoneId = parking.getParkingZoneId();
        this.expiration = parking.getExpiration();
        this.email = parking.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public UUID getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(UUID parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

