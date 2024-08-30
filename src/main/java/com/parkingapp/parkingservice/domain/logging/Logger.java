package com.parkingapp.parkingservice.domain.logging;

public interface Logger {

    void logInfo(String message);

    void logError(String message);
}
