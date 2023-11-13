package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaCitiesRepository implements CitiesRepository {

    @Override
    public List<City> getAllCities() {
        return null;
    }
}