package com.parkingapp.parkingservice.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.model.CityDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class CitiesRepository implements ICitiesRepository {
    private final List<CityDTO> cityDB;

    public CitiesRepository() {
        this.cityDB = loadCityDB();
    }

    @Override
    public List<CityDTO> getAll() {
        List<CityDTO> cities = cityDB.stream().toList();
        return cities;
    }

    private List<CityDTO> loadCityDB() {
        List<CityDTO> ret = null;

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:city.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<CityDTO>> typeRef = new TypeReference<>() {
        };

        try {
            ret = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}