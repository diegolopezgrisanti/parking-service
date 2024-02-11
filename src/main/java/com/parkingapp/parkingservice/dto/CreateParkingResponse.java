package com.parkingapp.parkingservice.dto;

import com.parkingapp.parkingservice.model.Parking;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.logging.log4j.CloseableThreadContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateParkingResponse {

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
            description = "City ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    private UUID city_id;

    @Schema(
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    private UUID parking_zone_id;

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

    public CreateParkingResponse(Parking parking) {
        this.id = UUID.randomUUID();
        this.plate = parking.getPlate();
        this.city_id = parking.getCity_id();
        this.parking_zone_id = parking.getParking_zone_id();
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

    public UUID getCity_id() {
        return city_id;
    }

    public void setCity_id(UUID city_id) {
        this.city_id = city_id;
    }

    public UUID getParking_zone_id() {
        return parking_zone_id;
    }

    public void setParking_zone_id(UUID parking_zone_id) {
        this.parking_zone_id = parking_zone_id;
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

