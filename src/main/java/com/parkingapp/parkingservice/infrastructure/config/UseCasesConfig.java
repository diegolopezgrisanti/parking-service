package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.application.createVehicle.CreateVehicleUseCase;
import com.parkingapp.parkingservice.application.createparking.CreateParkingUseCase;
import com.parkingapp.parkingservice.application.findallcities.FindAllCitiesUseCase;
import com.parkingapp.parkingservice.application.getparkingbyid.GetParkingByIdUseCase;
import com.parkingapp.parkingservice.application.checkparkingstatus.CheckParkingStatusUseCase;
import com.parkingapp.parkingservice.application.getparkingzones.GetParkingZonesByIdUseCase;
import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
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

    @Bean
    public GetParkingZonesByIdUseCase getParkingZonesByIdUseCase(
            ParkingZonesRepository parkingZonesRepository
    ) {
        return new GetParkingZonesByIdUseCase(parkingZonesRepository);
    }

    @Bean
    public CreateParkingUseCase createParkingUseCase(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository
    ) {
        return new CreateParkingUseCase(parkingRepository, parkingZonesRepository);
    }

    @Bean
    public GetParkingByIdUseCase getParkingByIdUseCase(
            ParkingRepository parkingRepository
    ) {
        return new GetParkingByIdUseCase(parkingRepository);
    }

    @Bean
    public CheckParkingStatusUseCase getParkingStatusCheckUseCase(
            ParkingRepository parkingRepository
    ) {
        return new CheckParkingStatusUseCase(parkingRepository);
    }

    @Bean
    public CreateVehicleUseCase createVehicleUseCase(
            VehicleRepository vehicleRepository
    ) {
        return new CreateVehicleUseCase(vehicleRepository);
    }
}
