import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.adarshr.test-logger") version "4.0.0"
	kotlin("jvm") version "2.0.10"
	kotlin("plugin.spring") version "2.0.10"
}

group = "com.parkingapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	val REST_ASSURED = "5.5.0"

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
	implementation("org.jetbrains:annotations:24.1.0")
	implementation("org.postgresql:postgresql:42.7.3")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.2")
	implementation("org.flywaydb:flyway-core:9.11.0")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.javamoney:moneta:1.4.4")

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")

	// Lombok
	testCompileOnly("org.projectlombok:lombok:1.18.34")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.3"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.assertj:assertj-core:3.26.3")
	testImplementation("org.mockito:mockito-core:5.+")

	testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.1"))
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("io.rest-assured:rest-assured:$REST_ASSURED")
	testImplementation("io.rest-assured:json-path:$REST_ASSURED")
	testImplementation("io.rest-assured:xml-path:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-mock-mvc:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-commons:$REST_ASSURED")
}

tasks.apply {
	test {
		enableAssertions = true
		useJUnitPlatform {
			excludeTags("integration")
			excludeTags("component")
			excludeTags("contract")
		}
	}

	testlogger {
		theme = ThemeType.STANDARD_PARALLEL
		showExceptions = true
		showStackTraces = true
		showFullStackTraces = false
		showCauses = true
		slowThreshold = 5000
		showSummary = true
		showSimpleNames = false
		showPassed = false
		showSkipped = true
		showFailed = true
		showOnlySlow = false
		showStandardStreams = false
		showPassedStandardStreams = false
		showSkippedStandardStreams = true
		showFailedStandardStreams = true
		logLevel = LogLevel.LIFECYCLE
	}

	task<Test>("integrationTest") {
		group = "verification"
		description = "Runs integration tests."
		useJUnitPlatform {
			includeTags("integration")
		}
		shouldRunAfter(test)
	}

	task<Test>("contractTest") {
		group = "verification"
		description = "Runs contract tests."
		useJUnitPlatform {
			includeTags("contract")
		}
		shouldRunAfter("integrationTest")
	}

	task<Test>("componentTest") {
		group = "verification"
		description = "Runs component tests."
		useJUnitPlatform {
			includeTags("component")
		}
		shouldRunAfter("contractTest")
	}

	check {
		dependsOn("integrationTest", "contractTest", "componentTest")
	}

}
