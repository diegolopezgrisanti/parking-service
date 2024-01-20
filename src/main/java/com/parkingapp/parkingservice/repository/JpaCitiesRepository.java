package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaCitiesRepository implements CitiesRepository {

    @Autowired
    private JpaCitiesInterfaceRepository jpaCitiesInterfaceRepository;

    @Override
    public List<City> getAllCities() {
        return jpaCitiesInterfaceRepository.findAll().stream()
                .map(cityEntity -> new City(cityEntity.getId(), cityEntity.getName()))
                .collect(Collectors.toList());
    }
}