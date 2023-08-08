package com.parkingapp.parkingservice.dto;

import java.util.List;

public class CitiesResponse {

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
