package com.parkingapp.parkingservice.infrastructure.config;

import com.parkingapp.parkingservice.application.createVehicle.CreateVehicleUseCase;
import com.parkingapp.parkingservice.application.createparking.CreateParkingUseCase;
import com.parkingapp.parkingservice.application.findallcities.FindAllCitiesUseCase;
import com.parkingapp.parkingservice.application.getparkingbyid.GetParkingByIdUseCase;
import com.parkingapp.parkingservice.application.checkparkingstatus.CheckParkingStatusUseCase;
import com.parkingapp.parkingservice.application.getparkingzones.GetParkingZonesUseCase;
import com.parkingapp.parkingservice.application.getuservehicles.GetUserVehiclesUseCase;
import com.parkingapp.parkingservice.application.parkingclosure.ParkingClosureUseCase;
import com.parkingapp.parkingservice.domain.atomicity.AtomicOperation;
import com.parkingapp.parkingservice.domain.city.CitiesRepository;
import com.parkingapp.parkingservice.domain.logging.LoggerFactory;
import com.parkingapp.parkingservice.domain.parkingclosure.ParkingClosureRepository;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import com.parkingapp.parkingservice.domain.parking.ParkingRepository;
import com.parkingapp.parkingservice.domain.payment.ParkingPaymentService;
import com.parkingapp.parkingservice.domain.vehicle.VehicleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class UseCasesConfig {

    @Bean
    public FindAllCitiesUseCase findAllCitiesUseCase(
            CitiesRepository citiesRepository
    ) {
        return new FindAllCitiesUseCase(citiesRepository);
    }

    @Bean
    public GetParkingZonesUseCase getParkingZonesUseCase(
            ParkingZonesRepository parkingZonesRepository
    ) {
        return new GetParkingZonesUseCase(parkingZonesRepository);
    }

    @Bean
    public CreateParkingUseCase createParkingUseCase(
            ParkingRepository parkingRepository,
            ParkingZonesRepository parkingZonesRepository,
            VehicleRepository vehicleRepository
    ) {
        return new CreateParkingUseCase(parkingRepository, parkingZonesRepository, vehicleRepository);
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

    @Bean
    public GetUserVehiclesUseCase getUserVehiclesUseCase(
            VehicleRepository vehicleRepository
    ) {
        return new GetUserVehiclesUseCase(vehicleRepository);
    }

    @Bean
    public ParkingClosureUseCase parkingClosureUseCase(
            ParkingClosureRepository parkingClosureRepository,
            ParkingPaymentService parkingPaymentService,
            AtomicOperation atomicOperation,
            Clock clock,
            LoggerFactory loggerFactory
    ) {
        return new ParkingClosureUseCase(
                parkingClosureRepository,
                parkingPaymentService,
                atomicOperation,
                clock,
                loggerFactory
        );
    }
}
