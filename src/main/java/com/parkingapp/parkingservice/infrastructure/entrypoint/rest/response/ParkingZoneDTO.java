package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.common.Currency;
import com.parkingapp.parkingservice.domain.common.Location;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

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
    private Currency currency;

    @Schema(
            description = "Fee per minute in cents",
            example = "100"
    )
    private int feePerMinute;

    public ParkingZoneDTO(UUID id, String name, Location location, Currency currency, int feePerMinute) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.currency = currency;
        this.feePerMinute = feePerMinute;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public Currency getCurrency() { return currency; }

    public void setCurrency(Currency currency) { this.currency = currency; }

    public int getFeePerMinute() { return feePerMinute; }

    public void setFeePerMinute(int feePerMinute) { this.feePerMinute = feePerMinute; }
}
