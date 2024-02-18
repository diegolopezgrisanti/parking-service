package com.parkingapp.parkingservice.repository;

import com.parkingapp.parkingservice.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface JpaParkingInterfaceRepository extends JpaRepository<ParkingEntity, UUID> {
}
