package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.common.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.money.CurrencyUnit;
import java.util.UUID;

@Data
public class ParkingZoneDTO {
    @Schema(
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    private UUID id;

    @Schema(
            description = "Parking zone name",
            example = "Sa Conca"
    )
    private String name;

    @Schema(
            description = "Parking zone location (Lat & Long)",
            example = "(40.71288, -74.00601)"
    )
    private Location location;

    @Schema(
            description = "Parking zone currency",
            example = "EUR"
    )
    private String currency;

    @Schema(
            description = "Fee per minute in cents",
            example = "100"
    )
    private int feePerMinute;

    public ParkingZoneDTO(UUID id, String name, Location location, CurrencyUnit currency, int feePerMinute) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.currency = currency.getCurrencyCode();
        this.feePerMinute = feePerMinute;
    }
}
