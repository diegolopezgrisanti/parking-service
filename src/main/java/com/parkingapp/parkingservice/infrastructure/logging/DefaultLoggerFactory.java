package com.parkingapp.parkingservice.infrastructure.logging;

import com.parkingapp.parkingservice.domain.logging.Logger;
import com.parkingapp.parkingservice.domain.logging.LoggerFactory;

public class DefaultLoggerFactory implements LoggerFactory {

    @Override
    public Logger getLogger(Class<?> clazz) {
        return new Log4jLogger(clazz);
    }
}
