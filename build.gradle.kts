import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
	java
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.adarshr.test-logger") version "4.0.0"
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")
	implementation("org.jetbrains:annotations:24.0.0")
	implementation("org.postgresql:postgresql:42.6.0")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.3")
	implementation("org.flywaydb:flyway-core:9.11.0")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.10.2"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.assertj:assertj-core:3.25.1")
	testImplementation("org.mockito:mockito-core:3.+")

	testImplementation(platform("org.testcontainers:testcontainers-bom:1.19.7"))
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.apply {
	test {
		enableAssertions = true
		useJUnitPlatform {
			excludeTags("integration")
			excludeTags("component")
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
}
