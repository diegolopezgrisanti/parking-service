package com.parkingapp.parkingservice.infrastructure.database;

import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.city.City;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyMap;

public class JdbcCitiesRepository implements CitiesRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCitiesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<City> getAllCities() {
        return namedParameterJdbcTemplate.query(
                """
                   select * from cities
                """,
                emptyMap(),
                new CityRowMapper()
        );
    }

    private class CityRowMapper implements RowMapper<City> {
        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new City(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name")
            );
        }
    }
}
