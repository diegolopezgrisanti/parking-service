package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.application.parkingclosure.ParkingClosureUseCase;
import com.parkingapp.parkingservice.infrastructure.scheduler.ParkingClosureScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulersConfig {

    @Bean
    public ParkingClosureScheduler parkingClosureScheduler(
            ParkingClosureUseCase parkingClosureUseCase,
            @Value("${scheduler.parking-closure.batchSize}") int batchSize
    ) {
        return new ParkingClosureScheduler(parkingClosureUseCase, batchSize);
    }
}
