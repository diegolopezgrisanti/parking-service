package com.parkingapp.parkingservice.domain.parkingclosure;

import com.parkingapp.parkingservice.domain.common.Amount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ParkingClosure {
    Amount feePerMinute;
    Instant startDate;
    Instant endDate;
    UUID parkingId;

}
