package com.parkingapp.parkingservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class CityDTO {

    @Schema(
            description = "City ID",
            example = "3"
    )
    private Integer id;

    @Schema(
            description = "City name",
            example = "Barcelona"
    )
    private String name;

    public CityDTO(Integer id, String name) {
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
