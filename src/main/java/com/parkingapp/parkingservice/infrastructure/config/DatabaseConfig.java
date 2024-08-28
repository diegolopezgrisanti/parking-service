package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import com.parkingapp.parkingservice.infrastructure.database.JdbcCitiesRepository;
import com.parkingapp.parkingservice.infrastructure.database.JdbcParkingClosureRepository;
import com.parkingapp.parkingservice.infrastructure.database.JdbcParkingRepository;
import com.parkingapp.parkingservice.infrastructure.database.JdbcParkingZonesRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.infrastructure.database.JdbcVehiclesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    public CitiesRepository citiesRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcCitiesRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ParkingRepository parkingRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcParkingRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ParkingZonesRepository parkingZonesRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcParkingZonesRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public VehicleRepository vehicleRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcVehiclesRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public ParkingClosureRepository parkingClosureRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcParkingClosureRepository(namedParameterJdbcTemplate);
    }
}
