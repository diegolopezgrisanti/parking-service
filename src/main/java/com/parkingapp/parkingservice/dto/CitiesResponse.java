package com.parkingapp.parkingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CitiesResponse {

    @Schema(
            description = "List of cities"
    )
    private List<CityDTO> cities;

    public CitiesResponse(List<CityDTO> cities) {
        this.cities = cities;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }


}
