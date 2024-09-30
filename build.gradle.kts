import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.adarshr.test-logger") version "4.0.0"
	kotlin("jvm") version "2.0.20"
	kotlin("plugin.spring") version "2.0.20"
	jacoco
}

group = "com.parkingapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	val REST_ASSURED = "5.5.0"
	val RETROFIT = "2.11.0"

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
	implementation("org.jetbrains:annotations:25.0.0")
	implementation("org.postgresql:postgresql:42.7.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.4")
	implementation("org.flywaydb:flyway-core:9.11.0")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.javamoney:moneta:1.4.4")
	implementation("com.squareup.retrofit2:retrofit:$RETROFIT")
	implementation("com.squareup.retrofit2:converter-jackson:$RETROFIT")
	implementation("io.github.resilience4j:resilience4j-retry:2.2.0")

	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	modules {
		module("org.springframework.boot:spring-boot-starter-logging") {
			replacedBy(
				"org.springframework.boot:spring-boot-starter-log4j2",
				"Use Log4j2 instead of Logback"
			)
		}
	}

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")

	// Lombok
	testCompileOnly("org.projectlombok:lombok:1.18.34")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:5.11.0"))
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
	testImplementation("org.wiremock:wiremock-standalone:3.9.1")
	testImplementation("org.awaitility:awaitility:4.2.2")
	testImplementation("com.tngtech.archunit:archunit:1.3.0")
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.apply {
	test {
		enableAssertions = true
		useJUnitPlatform {
			excludeTags("integration")
			excludeTags("component")
			excludeTags("contract")
		}
		finalizedBy(jacocoTestReport)
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

	jacocoTestReport {
		val jacocoDir = layout.buildDirectory.dir("jacoco")
		executionData(
			fileTree(jacocoDir).include(
			"/test.exec",
			"/contractTest.exec",
			"/integrationTest.exec"
			)
		)
		reports {
			csv.required.set(false)
			html.required.set(true)
			xml.required.set(true)
			html.outputLocation.set(layout.buildDirectory.dir("jacoco/html"))
			xml.outputLocation.set(layout.buildDirectory.file("jacoco/report.xml"))
		}
		dependsOn(test, "integrationTest", "contractTest")
	}

	jacocoTestCoverageVerification {
		val jacocoDir = layout.buildDirectory.dir("jacoco")
		executionData(
			fileTree(jacocoDir).include(
				"test.exec",
				"contractTest.exec",
				"integrationTest.exec"
			)
		)
		violationRules {
			rule {
				limit {
					minimum = "0.90".toBigDecimal()
				}
			}
		}
		dependsOn(test, "integrationTest", "contractTest")
	}
}
