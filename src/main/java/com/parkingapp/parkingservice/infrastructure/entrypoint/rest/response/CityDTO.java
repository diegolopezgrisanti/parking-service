package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CityDTO {

    @Schema(
            description = "City ID",
            example = "5f215120-c669-451a-97b1-57f79144548b"
    )
    private UUID id;

    @Schema(
            description = "City name",
            example = "Barcelona"
    )
    private String name;
}
