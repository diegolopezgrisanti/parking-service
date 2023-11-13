package com.parkingapp.parkingservice.repository;


import com.parkingapp.parkingservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CitiesRepository extends JpaRepository<City, UUID> {
}
