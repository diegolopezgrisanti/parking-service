package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.CitiesResponse;
import com.parkingapp.parkingservice.dto.CityDTO;
import com.parkingapp.parkingservice.dto.ErrorResponse;
import com.parkingapp.parkingservice.model.City;
import com.parkingapp.parkingservice.service.CitiesService;
import com.parkingapp.parkingservice.service.CitiesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
@Tag(name = "Cities", description = "All about cities")
public class CitiesController {
    private final CitiesServiceImpl citiesServiceImpl;

    public CitiesController(CitiesServiceImpl citiesService) {
        this.citiesServiceImpl = citiesService;
    }

    @Operation(summary = "List cities")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation =CitiesResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            )
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CitiesResponse getCities() {

        List<City> cities = citiesServiceImpl.findAll();
        return new CitiesResponse(cities);
    }
}