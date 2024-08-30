package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.domain.logging.LoggerFactory;
import com.parkingapp.parkingservice.infrastructure.logging.DefaultLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    LoggerFactory loggerFactory() {
        return new DefaultLoggerFactory();
    }
}
