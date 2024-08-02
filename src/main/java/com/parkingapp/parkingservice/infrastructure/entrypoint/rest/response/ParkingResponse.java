package com.parkingapp.parkingservice.infrastructure.entrypoint.rest.response;

import com.parkingapp.parkingservice.domain.parking.Parking;
import com.parkingapp.parkingservice.domain.parking.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
public class ParkingResponse {

    @Schema(
            description = "Parking ID",
            example = "b95f3649-fe78-44a1-b345-5dcb318523e1"
    )
    private UUID id;

    @Schema(
            name = "parking_zone_id",
            description = "Parking zone ID",
            example = "8e4488d3-0e5a-4044-8d6d-d3d9e36836d0"
    )
    private UUID parkingZoneId;

    @Schema(
            name = "user_id",
            description = "User ID",
            example = "685275ca-d4d4-4465-b2ac-4d7451eeffef"
    )
    private UUID userId;

    @Schema(
            name = "vehicle_id",
            description = "Vehicle ID",
            example = "1ef08577-d7eb-455d-8152-c7b620e5b8cc"
    )
    private UUID vehicleId;

    @Schema(
            name = "payment_method_id",
            description = "Payment method ID",
            example = "5bc31e3e-4057-425a-bda8-3e5913d56e95"
    )
    private UUID paymentMethodId;

    @Schema(
            name = "start_date",
            description = "Parking start date time with zone",
            example = "2024-12-24T16:18:58.165+00:00"
    )
    private Instant startDate;

    @Schema(
            name = "end_date",
            description = "Parking end date time with zone",
            example = "2024-12-24T17:18:58.165+00:00"
    )
    private Instant endDate;

    @Schema(
            name = "payment_status",
            description = "Parking payment status",
            example = "PENDING"
    )
    private PaymentStatus paymentStatus;

    public ParkingResponse(Parking parking) {
        this.id = parking.getId();
        this.parkingZoneId = parking.getParkingZoneId();
        this.vehicleId = parking.getVehicleId();
        this.userId = parking.getUserId();
        this.paymentMethodId = parking.getPaymentMethodId();
        this.startDate = parking.getStartDate();
        this.endDate = parking.getEndDate();
        this.paymentStatus = parking.getPaymentStatus();
    }
}

