package com.parkingapp.parkingservice.infrastructure.fixtures.initializers.testannotation;

import com.parkingapp.parkingservice.infrastructure.config.DatabaseConfig;
import com.parkingapp.parkingservice.infrastructure.fixtures.initializers.PostgreSqlContainer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ContextConfiguration(initializers = PostgreSqlContainer.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        DatabaseConfig.class
})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithPostgreSql {
}
