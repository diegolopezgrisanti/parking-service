package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings", description = "All about parking")
public class ParkingsController {

    @Operation(summary = "Create parking")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateParkingResponse.class)
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateParkingResponse createParking(@RequestBody @Valid CreateParkingRequest request) {

        CreateParkingResponse response = new CreateParkingResponse();
        response.setId(UUID.randomUUID());
        response.setPlate(request.getPlate());
        response.setCity_id(request.getCity_id());
        response.setParking_zone_id(request.getParking_zone_id());
        response.setExpiration(request.getExpiration());
        response.setEmail(request.getEmail());

        return response;
    }
}
