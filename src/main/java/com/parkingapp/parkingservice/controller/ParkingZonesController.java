package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.ErrorResponse;
import com.parkingapp.parkingservice.dto.ParkingZoneDTO;
import com.parkingapp.parkingservice.dto.ParkingZonesResponse;
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

@RestController
@RequestMapping("/parking-zones")
@Tag(name = "Parking zones", description = "All about parking zones")
public class ParkingZonesController {

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
            schema = @Schema(type = "string", format = "uuid", example = "48236e54-017a-446a-b1b5-8e1d85222cc8")
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ParkingZonesResponse getParkingZoneById(@RequestParam("city-id") String cityId) {
        // Mock response
        System.out.println(String.format("City Id requested is: %s", cityId));
       List<ParkingZoneDTO> dummyParkingZones = List.of(new ParkingZoneDTO(1234, "Sa Conca"), new ParkingZoneDTO(253, "Port"));
       return new ParkingZonesResponse(dummyParkingZones);
    }
}
