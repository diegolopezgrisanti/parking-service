package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.city.City;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.IntegrationTest;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation.WithPostgreSql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@WithPostgreSql
public class JdbcCitiesRepositoryIntegrationTest {

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    public void setUp() {
        JdbcTestUtils.deleteFromTables(
                namedParameterJdbcTemplate.getJdbcTemplate(),
                "parking_zones",
                "cities"
        );
    }

    @Test
    public void shouldFindAllCities() {
        // GIVEN
        City city = new City(UUID.randomUUID(), "Barcelona");
        City city2 = new City(UUID.randomUUID(), "Girona");
        List<City> expectedCities = List.of(city, city2);

        givenExitingCity(city);
        givenExitingCity(city2);

        // WHEN
        List<City> result = citiesRepository.getAllCities();

        // THEN
        assertThat(result).isEqualTo(expectedCities);
    }

    private void givenExitingCity(City city) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", city.getId())
                .addValue("name", city.getName());

        namedParameterJdbcTemplate.update(
                "INSERT INTO cities(id, name) VALUES (:id, :name);",
                params
        );
    }

}
