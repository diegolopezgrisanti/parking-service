package com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation;

import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.WiremockTestContainer;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ContextConfiguration(initializers = WiremockTestContainer.class)
@Target({ElementType.TYPE})
public @interface WithWireMock {
}
