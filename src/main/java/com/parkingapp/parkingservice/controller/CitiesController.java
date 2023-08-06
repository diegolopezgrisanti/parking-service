package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.model.CityDTO;
import com.parkingapp.parkingservice.service.CitiesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CitiesController {
    private final CitiesService citiesService;

    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getCities() {
        return citiesService.getCities();
    }
}