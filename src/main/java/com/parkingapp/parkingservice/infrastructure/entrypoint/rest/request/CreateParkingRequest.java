package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.request;

import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateParkingRequest {

    @Schema(
            description = "Vehicle plate",
            example = "4736KTZ"
    )
    @NotNull
    @Pattern(regexp = "\\d{4}[A-Za-z]{3}")
    private String plate;

    @Schema(
            name = "parking_zone_id",
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    @NotNull
    private UUID parkingZoneId;

    @Schema(
            name = "user_id",
            description = "User ID",
            example = "685275ca-d4d4-4465-b2ac-4d7451eeffef"
    )
    @NotNull
    private UUID userId;

    @Schema(
            name = "vehicle_id",
            description = "Vehicle ID",
            example = "1ef08577-d7eb-455d-8152-c7b620e5b8cc"
    )
    @NotNull
    private UUID vehicleId;

    @Schema(
            name = "payment_method_id",
            description = "Payment method ID",
            example = "5bc31e3e-4057-425a-bda8-3e5913d56e95"
    )
    @NotNull
    private UUID paymentMethodId;

    @Schema(
            name = "start_date",
            description = "Parking start date time with zone",
            example = "2024-12-24T16:18:58.165+00:00"
    )
    @NotNull
    @Future
    private Instant startDate;

    @Schema(
            name = "end_date",
            description = "Parking end date time with zone",
            example = "2024-12-24T17:18:58.165+00:00"
    )
    @NotNull
    @Future
    private Instant endDate;

    @Schema(
            name = "payment_status",
            description = "Parking payment status",
            example = "PENDING"
    )
    @NotNull
    private PaymentStatus paymentStatus;
}
