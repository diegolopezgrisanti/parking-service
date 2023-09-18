package com.parkingapp.parkingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class ParkingZoneDTO {
    @Schema(
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    private UUID id;

    @Schema(
            description = "Parking zone name",
            example = "Sa Conca"
    )
    private String name;

    public ParkingZoneDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
