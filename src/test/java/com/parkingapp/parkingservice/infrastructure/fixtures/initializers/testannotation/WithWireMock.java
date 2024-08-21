package com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation;

import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.WiremockTestContainer;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ContextConfiguration(initializers = WiremockTestContainer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithWireMock {
}
