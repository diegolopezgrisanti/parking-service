package com.parkingapp.parkingservice.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingapp.parkingservice.model.City;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class CitiesJSONRepository implements CitiesRepository {
    private final List<City> cityDB;

    public CitiesJSONRepository() {
        this.cityDB = loadCityDB();
    }

    @Override
    public List<City> getAll() {
        List<City> cities = cityDB.stream().toList();
        return cities;
    }

    private List<City> loadCityDB() {
        List<City> ret = null;

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:city.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<City>> typeRef = new TypeReference<>() {
        };

        try {
            ret = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
