package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.City;
import com.parkingapp.parkingservice.repository.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {

    private final CitiesRepository citiesRepository;

    @Autowired
    public CitiesServiceImpl(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public List<City> findAll() {
        return citiesRepository.getAllCities();
    }
}
