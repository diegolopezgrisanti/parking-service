package com.parkingapp.parkingservice.application.findallcities;

import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.city.City;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAllCitiesUseCaseTest {

    private CitiesRepository citiesRepository = mock(CitiesRepository.class);
    private FindAllCitiesUseCase useCase = new FindAllCitiesUseCase(citiesRepository);

    @Test
    void shouldReturnAllCities() {
        // GIVEN
        List<City> cities = List.of(new City(UUID.randomUUID(), "TEST"));
        when(citiesRepository.getAllCities()).thenReturn(cities);

        // WHEN
        List<City> result = useCase.execute();

        // THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(cities);

        verify(citiesRepository, times(1)).getAllCities();
    }

}
