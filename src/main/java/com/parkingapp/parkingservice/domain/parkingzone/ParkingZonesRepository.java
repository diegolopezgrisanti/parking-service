package com.parkingapp.parkingservice.domain.parkingzone;

import java.util.List;
import java.util.UUID;

public interface ParkingZonesRepository {
    List<ParkingZone> getParkingZonesByCityId(UUID cityId);

    boolean isParkingZoneIdValid(UUID parkingZoneId0);
}
