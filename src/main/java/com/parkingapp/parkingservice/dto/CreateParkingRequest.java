package com.parkingapp.parkingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

public class CreateParkingRequest {

    @Schema(
            description = "Vehicle plate",
            example = "4736KTZ"
    )
    @NotNull
    @Pattern(regexp = "\\d{4}[A-Za-z]{3}")
    private String plate;

    @Schema(
            name = "city_id",
            description = "City ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    @NotNull
    private UUID cityId;

    @Schema(
            name = "parking_zone_id",
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    @NotNull
    private UUID parkingZoneId;

    @Schema(
            description = "Parking expiration date time with zone",
            example = "2023-09-18T16:18:58.165+00:00"
    )
    @NotNull
    @Future
    private Instant expiration;

    @Schema(
            description = "User email",
            example = "dummy_user@mail.com"
    )
    @NotNull
    @Email(message = "Must be a valid email")
    @NotBlank
    private String email;

    public CreateParkingRequest(){}

    public CreateParkingRequest(String plate, UUID cityId, UUID parkingZoneId, Instant expiration, String email) {
        this.plate = plate;
        this.cityId = cityId;
        this.parkingZoneId = parkingZoneId;
        this.expiration = expiration;
        this.email = email;
    }

    public String getPlate() {
        return plate;
    }

    public UUID getCityId() {
        return cityId;
    }

    public UUID getParkingZoneId() {
        return parkingZoneId;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public String getEmail() {
        return email;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public void setParkingZoneId(UUID parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
