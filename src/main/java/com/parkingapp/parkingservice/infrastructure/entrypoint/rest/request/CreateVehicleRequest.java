package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVehicleRequest {

    @Schema(
            description = "Brand",
            example = "Ford"
    )
    @NotNull
    private String brand;

    @Schema(
            description = "Model",
            example = "Focus"
    )
    @NotNull
    private String model;

    @Schema(
            description = "Color",
            example = "red"
    )
    @NotNull
    private String color;

    @Schema(
            description = "Plate",
            example = "4679KYY"
    )
    @NotNull
    @Pattern(regexp = "\\d{4}[A-Za-z]{3}")
    private String plate;

    @Schema(
            description = "Country",
            example = "ES"
    )
    @NotNull
    private String country;

    @Schema(
            name = "user_id",
            description = "User ID",
            example = "6e503bda-ef3d-4efc-b024-c3e949b81890"
    )
    @NotNull
    private UUID userId;
}
