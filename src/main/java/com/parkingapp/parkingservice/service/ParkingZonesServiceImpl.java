package com.parkingapp.parkingservice.service;

import com.parkingapp.parkingservice.domain.parkingzone.ParkingZone;
import com.parkingapp.parkingservice.domain.parkingzone.ParkingZonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingZonesServiceImpl implements ParkingZonesService {

    private final ParkingZonesRepository parkingZonesRepository;

    @Autowired
    public ParkingZonesServiceImpl(ParkingZonesRepository parkingZonesRepository) {
        this.parkingZonesRepository = parkingZonesRepository;
    }

    public List<ParkingZone> getAllByCityId(UUID cityId) {
        return parkingZonesRepository.getParkingZonesByCityId(cityId);
    }

}
