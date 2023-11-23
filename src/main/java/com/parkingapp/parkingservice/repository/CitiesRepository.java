package com.parkingapp.parkingservice.repository;


import com.parkingapp.parkingservice.model.City;

import java.util.List;

public interface CitiesRepository {
    List<City> getAllCities();
}
