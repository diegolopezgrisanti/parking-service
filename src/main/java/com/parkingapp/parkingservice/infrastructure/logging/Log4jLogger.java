package com.parkingapp.parkingservice.infrastructure.logging;

import com.parkingapp.parkingservice.domain.logging.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4jLogger implements Logger {

    private final org.apache.logging.log4j.Logger log;

    public Log4jLogger(Class<?> clazz) {
        this.log = LogManager.getLogger(clazz);
    }

    @Override
    public void logInfo(String message) {
        log.info(message);
    }

    @Override
    public void logError(String message) {
        log.error(message);
    }
}