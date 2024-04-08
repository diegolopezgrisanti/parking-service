package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.application.findallcities.FindAllCitiesUseCase;
import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public FindAllCitiesUseCase findAllCitiesUseCase(
            CitiesRepository citiesRepository
    ) {
        return new FindAllCitiesUseCase(citiesRepository);
    }
}
