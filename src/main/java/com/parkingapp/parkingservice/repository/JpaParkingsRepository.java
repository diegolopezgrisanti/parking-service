package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingEntity;
import com.parkingapp.parkingservice.model.Parkings;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaParkingsRepository implements ParkingsRepository {

    @Autowired
    private JpaParkingsInterfaceRepository jpaParkingsInterfaceRepository;

    @Override
    public Parkings createParking() {
//        return jpaParkingsInterfaceRepository.save();
        return null;
    }

}
