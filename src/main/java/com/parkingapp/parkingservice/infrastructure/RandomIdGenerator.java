package com.parkingapp.parkingservice.infrastructure;

import com.parkingapp.parkingservice.domain.common.IdGenerator;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
