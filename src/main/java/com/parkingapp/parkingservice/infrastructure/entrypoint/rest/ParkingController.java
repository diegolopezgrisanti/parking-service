package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.parkingapp.parkingservice.application.createparking.CreateParkingUseCase;
import com.parkingapp.parkingservice.application.getparkingbyid.GetParkingByIdUseCase;
import com.parkingapp.parkingservice.application.checkparkingstatus.CheckParkingStatusUseCase;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.request.CreateParkingRequest;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingCheckResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingDetailsDTO;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.ParkingStatusCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/parkings")
@Tag(name = "Parkings", description = "All about parking")
public class ParkingController {

    private final CreateParkingUseCase createParkingUseCase;
    private final GetParkingByIdUseCase getParkingByIdUseCase;
    private final CheckParkingStatusUseCase checkParkingStatusUseCase;

    public ParkingController(
            CreateParkingUseCase createParkingUseCase,
            GetParkingByIdUseCase getParkingByIdUseCase,
            CheckParkingStatusUseCase checkParkingStatusUseCase
    ) {
        this.createParkingUseCase = createParkingUseCase;
        this.getParkingByIdUseCase = getParkingByIdUseCase;
        this.checkParkingStatusUseCase = checkParkingStatusUseCase;
    }

    @Operation(summary = "Create parking")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingResponse createParking(@RequestBody @Valid CreateParkingRequest request) {
        Parking parking = new Parking(
                UUID.randomUUID(),
                request.getParkingZoneId(),
                request.getPlate(),
                request.getEmail(),
                request.getExpiration()
        );
        createParkingUseCase.execute(parking);

        return new ParkingResponse(parking);
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
        ParkingStatusCheck check = checkParkingStatusUseCase.execute(plate, parkingZoneId);
        ParkingDetailsDTO parkingDetails = check.getParking() != null
                ? new ParkingDetailsDTO(plate, check.getParking().getExpiration())
                : null;

        return new ParkingCheckResponse(check.getParkingStatus(), parkingDetails);
    }

    @Operation(summary = "Get parking by id")
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
                    responseCode = "404",
                    description = "Parking was not found with the ID provided",
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
    @GetMapping("/{parkingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getParkingById(@PathVariable UUID parkingId) {
        Optional<Parking> parking = getParkingByIdUseCase.execute(parkingId);

        if (parking.isPresent()) {
            ParkingResponse response = new ParkingResponse(parking.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ErrorResponse errorResponse = new ErrorResponse("Parking not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
