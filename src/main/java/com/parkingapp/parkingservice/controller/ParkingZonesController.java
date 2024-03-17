package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.*;
import com.parkingapp.parkingservice.dto.error.ErrorResponse;
import com.parkingapp.parkingservice.model.ParkingZone;
import com.parkingapp.parkingservice.service.ParkingZonesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking-zones")
@Tag(name = "Parking zones", description = "All about parking zones")
public class ParkingZonesController {

    private final ParkingZonesService parkingZonesService;

    public ParkingZonesController(ParkingZonesService parkingZonesService) {
        this.parkingZonesService = parkingZonesService;
    }

    @Operation(summary = "Gets all parking zones by city ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParkingZonesResponse.class)
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
    @Parameter(
            name = "city-id",
            description = "City ID to filter parking zones",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string", format = "uuid", example = "dd9f8f22-7395-49fc-bcf7-67533742ae03")
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ParkingZonesResponse getParkingZonesByCityId(@RequestParam("city-id") UUID cityId) {
        List<ParkingZone> parkingZones = parkingZonesService.getAllByCityId(cityId);
        List<ParkingZoneDTO> mappedParkingZones = parkingZones.stream()
                .map(parkingZone -> new ParkingZoneDTO(parkingZone.getId(), parkingZone.getName()))
                .collect(Collectors.toList());

        return new ParkingZonesResponse(mappedParkingZones);
    }
}
