package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.*;
import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.service.ParkingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings", description = "All about parking")
public class ParkingController {

    private final ParkingServiceImpl parkingServiceImpl;

    public ParkingController(ParkingServiceImpl parkingService) {
        this.parkingServiceImpl = parkingService;
    }

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
    public CreateParkingResponse createParking(@RequestBody @Valid Parking request) {
        parkingServiceImpl.createParking(request);

        return new CreateParkingResponse();
    }
}
