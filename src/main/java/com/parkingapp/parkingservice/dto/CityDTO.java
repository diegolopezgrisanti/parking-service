package com.parkingapp.parkingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class CityDTO {

    @Schema(
            description = "City ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    private UUID id;

    @Schema(
            description = "City name",
            example = "Barcelona"
    )
    private String name;

    public CityDTO(UUID id, String name) {
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
