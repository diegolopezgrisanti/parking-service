package com.parkingapp.parkingservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class ParkingZoneDTO {
    @Schema(
            description = "Parking zone ID",
            example = "15"
    )
    private Integer id;

    @Schema(
            description = "Parking zone name",
            example = "Sa Conca"
    )
    private String name;

    public ParkingZoneDTO(Integer id, String name) {
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
