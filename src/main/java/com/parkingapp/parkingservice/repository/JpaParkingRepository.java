package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingEntity;
import com.parkingapp.parkingservice.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaParkingRepository implements ParkingRepository {

    @Autowired
    private JpaParkingInterfaceRepository jpaParkingInterfaceRepository;

    @Override
    public void saveParking(Parking parking) {

        ParkingEntity parkingEntity = new ParkingEntity();
        parkingEntity.setId(UUID.randomUUID());
        parkingEntity.setPlate(parking.getPlate());
        parkingEntity.setCityId(parking.getCityId());
        parkingEntity.setParkingZoneId(parking.getParkingZoneId());
        parkingEntity.setExpiration(parking.getExpiration());
        parkingEntity.setEmail(parking.getEmail());

        jpaParkingInterfaceRepository.save(parkingEntity);
    }
}
