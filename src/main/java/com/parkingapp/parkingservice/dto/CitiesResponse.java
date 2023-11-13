package com.parkingapp.parkingservice.dto;

import com.parkingapp.parkingservice.model.City;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CitiesResponse {

    @Schema(
            description = "List of cities"
    )
    private List<City> cities;

    public CitiesResponse(List<City> cities) {
        this.cities = cities;
    }

    public List<City> findAll() {
        return cities;
    }

}
