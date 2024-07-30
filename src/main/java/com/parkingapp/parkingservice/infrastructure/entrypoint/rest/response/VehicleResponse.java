package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.common.Country;
import com.parkingapp.parkingservice.domain.vehicle.Vehicle;
import com.parkingapp.parkingservice.domain.vehicle.VehicleColor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class VehicleResponse {
    @Schema(
            description = "Vehicle ID",
            example = "135132c4-619f-4f6f-992d-b52ac9026a05"
    )
    private UUID id;

    @Schema(
            description = "Brand",
            example = "Ford"
    )
    private String brand;

    @Schema(
            description = "Model",
            example = "Focus"
    )
    private String model;

    @Schema(
            description = "Color",
            example = "RED"
    )
    private VehicleColor color;

    @Schema(
            description = "Plate",
            example = "4679KYY"
    )
    private String plate;

    @Schema(
            description = "Country",
            example = "ESP"
    )
    private Country country;

    @Schema(
            name = "user_id",
            description = "User ID",
            example = "6e503bda-ef3d-4efc-b024-c3e949b81890"
    )
    private UUID userId;

    public  VehicleResponse(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.brand = vehicle.getBrand();
        this.model = vehicle.getModel();
        this.color = vehicle.getColor();
        this.plate = vehicle.getPlate();
        this.country = vehicle.getCountry();
        this.userId = vehicle.getUserId();
    }
}
