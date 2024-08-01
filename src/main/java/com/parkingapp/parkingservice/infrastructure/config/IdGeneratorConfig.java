package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.domain.common.IdGenerator;
import com.parkingapp.parkingservice.infrastructure.RandomIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }
}
