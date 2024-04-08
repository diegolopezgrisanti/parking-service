package com.parkingapp.parkingservice.application.findallcities;

import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.city.City;

import java.util.List;

public class FindAllCitiesUseCase {

    private final CitiesRepository citiesRepository;

    public FindAllCitiesUseCase(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public List<City> execute() {
        return citiesRepository.getAllCities();
    }
}
