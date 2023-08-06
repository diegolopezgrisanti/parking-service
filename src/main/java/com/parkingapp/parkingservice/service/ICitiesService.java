package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.model.CityDTO;

import java.util.List;

public interface ICitiesService {
    List<CityDTO> getCities();
}