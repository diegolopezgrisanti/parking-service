package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.City;
import com.parkingapp.parkingservice.repository.CitiesRepository;
import org.springframework.stereotype.Service;

@Service
public class CitiesServiceImpl implements CitiesService {
    private CitiesRepository citiesRepository;

    public CitiesServiceImpl(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @Override
    public java.util.List<City> getCities() {

        return citiesRepository.getAll();
    }
}
