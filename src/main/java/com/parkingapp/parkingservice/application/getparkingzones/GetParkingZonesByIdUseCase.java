package com.parkingapp.parkingservice.application.getparkingzones;

import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;

import java.util.List;
import java.util.UUID;

public class GetParkingZonesByIdUseCase {

    private final ParkingZonesRepository parkingZonesRepository;

    public GetParkingZonesByIdUseCase(ParkingZonesRepository parkingZonesRepository) {
        this.parkingZonesRepository = parkingZonesRepository;
    }

    public List<ParkingZone> execute(UUID cityId) {
        return parkingZonesRepository.getParkingZonesByCityId(cityId);
    }

}
