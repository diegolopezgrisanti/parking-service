package com.parkingapp.parkingservice.domain.parkingclosure;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ParkingClosureRepository {
    List<ParkingClosure> getParkingsWithPendingPayment(int batchSize, Instant endDateUntil);

    void markAsFailed(UUID parkingId, Instant processedAt);

    void markAsProcessed(UUID parkingId, Instant processedAt);
}
