package com.parkingapp.parkingservice.controller;

import com.parkingapp.parkingservice.dto.*;
import com.parkingapp.parkingservice.dto.error.ErrorResponse;
import com.parkingapp.parkingservice.model.Parking;
import com.parkingapp.parkingservice.model.ParkingStatusCheck;
import com.parkingapp.parkingservice.model.ParkingZone;
import com.parkingapp.parkingservice.service.ParkingService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings", description = "All about parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
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
    public CreateParkingResponse createParking(@RequestBody @Valid CreateParkingRequest request) {
        Parking parking = new Parking(
                UUID.randomUUID(),
                request.getParkingZoneId(),
                request.getPlate(),
                request.getEmail(),
                request.getExpiration()
        );
        parkingService.createParking(parking);

        return new CreateParkingResponse(parking);
    }

    @Operation(summary = "Check parking by plate and parking zone id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingCheckResponse.class)
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
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public ParkingCheckResponse checkParking(
            @RequestParam("plate") String plate,
            @RequestParam("parking_zone_id") UUID parkingZoneId
    ) {
        ParkingStatusCheck check = parkingService.getParkingStatusCheck(plate, parkingZoneId);
        ParkingDetailsDTO parkingDetails = check.getParking() != null
                ? new ParkingDetailsDTO(plate, check.getParking().getExpiration())
                : null;

        return new ParkingCheckResponse(check.getParkingStatus(), parkingDetails);
    }
    @GetMapping("/{parking_id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingResponse getParkingById(@PathVariable("parking_id") UUID id) {
        Parking parking = parkingService.getParkingById(id);
        ParkingResponse parkingById = parking.getId() != null
                ? new ParkingResponse(parking.getId())
                : null;

        return new ParkingResponse(parkingById);
    }
}
