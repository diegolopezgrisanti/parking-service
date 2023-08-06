package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.CityDTO;
import com.parkingapp.parkingservice.repository.CitiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesService implements ICitiesService {
    private CitiesRepository citiesRepository;

    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    @Override
    public List<CityDTO> getCities() {

        return citiesRepository.getAll();
    }
}