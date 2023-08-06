package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.model.CityDTO;

import java.util.List;

public interface ICitiesRepository{
    List<CityDTO> getAll();
}