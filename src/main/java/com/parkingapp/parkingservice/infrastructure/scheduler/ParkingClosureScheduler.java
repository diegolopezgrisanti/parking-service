package com.parkingapp.parkingservice.infrastructure.scheduler;

import com.parkingapp.parkingservice.application.parkingclosure.ParkingClosureUseCase;
import org.springframework.scheduling.annotation.Scheduled;

public class ParkingClosureScheduler {
    private final ParkingClosureUseCase parkingClosureUseCase;
    private final int batchSize;

    public ParkingClosureScheduler(
        ParkingClosureUseCase parkingClosureUseCase,
        int batchSize
    ) {
        this.parkingClosureUseCase = parkingClosureUseCase;
        this.batchSize = batchSize;
    }

    @Scheduled(fixedDelayString = "${scheduler.parking-closure.fixedDelay}")
    public void closeParking() {
        parkingClosureUseCase.execute(batchSize);
    }
}
