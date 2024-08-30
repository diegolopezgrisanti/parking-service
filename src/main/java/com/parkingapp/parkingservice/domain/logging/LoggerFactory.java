package com.parkingapp.parkingservice.domain.logging;

public interface LoggerFactory {
    Logger getLogger(Class<?> clazz);
}
