package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.infrastructure.database.JdbcCitiesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    public JdbcCitiesRepository jdbcCitiesRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new JdbcCitiesRepository(namedParameterJdbcTemplate);
    }

}
