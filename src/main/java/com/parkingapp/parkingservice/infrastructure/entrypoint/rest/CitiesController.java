package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.parkingapp.parkingservice.application.findallcities.FindAllCitiesUseCase;
import com.parkingapp.parkingservice.domain.city.City;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.CitiesResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.CityDTO;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
@Tag(name = "Cities", description = "All about cities")
public class CitiesController {
    private final FindAllCitiesUseCase findAllCitiesUseCase;

    public CitiesController(FindAllCitiesUseCase findAllCitiesUseCase) {
        this.findAllCitiesUseCase = findAllCitiesUseCase;
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

        List<City> cities = findAllCitiesUseCase.execute();
        List<CityDTO> mappedCities = cities.stream()
                        .map(city -> new CityDTO(city.getId(), city.getName()))
                        .collect(Collectors.toList());

        return new CitiesResponse(mappedCities);
    }
}
