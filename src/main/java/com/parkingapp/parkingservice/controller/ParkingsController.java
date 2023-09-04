package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.*;
import com.parkingapp.parkingservice.model.Parking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings", description = "All about parking")
public class ParkingsController {

    @Operation(summary = "Create parking")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingResponse.class)
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
//    @RequestBody(description = "Created Parking", required = true,
//            content = @Content(
//                    schema = @Schema(implementation = Parking.class)
//            )
//    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingResponse createParking(@RequestBody @Valid ParkingDTO parking) {
        // Mock response
        List<ParkingDTO> dummyParking = List.of(new ParkingDTO(24, "Salou"));
        return new ParkingResponse(dummyParking);
    }
}
