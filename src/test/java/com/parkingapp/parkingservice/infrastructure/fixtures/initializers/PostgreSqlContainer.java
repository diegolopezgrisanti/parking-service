package com.parkingapp.parkingservice.infrastructure.fixtures.initializers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class PostgreSqlContainer extends PostgreSQLContainer<PostgreSqlContainer>
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSqlContainer container;

    static {
        container = new PostgreSqlContainer()
                .withDatabaseName("parkingService")
                .withUsername("parkingService")
                .withPassword("secret")
                .waitingFor(Wait.forListeningPort());
    }

    public PostgreSqlContainer() {
        super("postgres:15");
    }

    public static PostgreSqlContainer getInstance() {
        return container;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        container.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + container.getJdbcUrl(),
                "spring.datasource.username=" + container.getUsername(),
                "spring.datasource.password=" + container.getPassword()
        ).applyTo(applicationContext);

        applicationContext.addApplicationListener(event -> {
            if (event instanceof ContextClosedEvent) {
                container.stop();
            }
        });
    }
}
