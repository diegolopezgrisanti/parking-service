package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

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
}
