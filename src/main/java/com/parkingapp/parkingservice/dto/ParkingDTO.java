package com.parkingapp.parkingservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class ParkingDTO {
    @Schema(
            description = "Parking ID",
            example = "24"
    )
    private Integer id;

    @Schema(
            description = "Parking name",
            example = "Salou"
    )
    private String name;

    public ParkingDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
