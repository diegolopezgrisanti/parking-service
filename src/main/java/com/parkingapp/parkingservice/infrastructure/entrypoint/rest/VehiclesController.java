package com.parkingapp.parkingservice.infrastructure.entrypoint.rest;

import com.parkingapp.parkingservice.application.createVehicle.CreateVehicleUseCase;
import com.parkingapp.parkingservice.application.getuservehicles.GetUserVehiclesUseCase;
import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.request.CreateVehicleRequest;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.ParkingResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.VehicleResponse;
import com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "All about vehicles")
public class VehiclesController {
    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetUserVehiclesUseCase getUserVehiclesUseCase;
    private final IdGenerator idGenerator;

    public VehiclesController(CreateVehicleUseCase createVehicleUseCase, GetUserVehiclesUseCase getUserVehiclesUseCase, IdGenerator idGenerator) {
        this.createVehicleUseCase = createVehicleUseCase;
        this.getUserVehiclesUseCase = getUserVehiclesUseCase;
        this.idGenerator = idGenerator;
    }

    @Operation(summary = "Create a new vehicle")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = VehicleResponse.class)
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
                    responseCode = "409",
                    description = "Vehicle for the userId with plate provided already exists",
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
    public VehicleResponse createVehicle(@RequestBody @Valid CreateVehicleRequest request) {
        Vehicle vehicle = new Vehicle(
                idGenerator.generate(),
                request.getBrand(),
                request.getModel(),
                request.getColor(),
                request.getPlate(),
                request.getCountry(),
                request.getUserId()
        );
        createVehicleUseCase.execute(vehicle);

        return new VehicleResponse(vehicle);
    }

    @Operation(summary = "Get user's vehicles by user ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = VehicleResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vehicles was not found with the user ID provided",
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
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vehicle> getUserVehiclesById(@PathVariable UUID userId) {
        return getUserVehiclesUseCase.execute(userId);
    }
}
