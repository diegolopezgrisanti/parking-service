package com.parkingapp.parkingservice.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class SpringArchitectureTest {
    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(DO_NOT_INCLUDE_TESTS)
            .importPackages("com.parkingapp.parkingservice");

    @Test
    public void shouldNotUseStereotypeAnnotationsForBeans() {
        classes()
                .should().notBeAnnotatedWith(Component.class)
                .andShould().notBeAnnotatedWith(Repository.class)
                .andShould().notBeAnnotatedWith(Service.class)
                .check(importedClasses);
    }
}
