import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  val kotlinVersion = "2.3.10"
  id("org.springframework.boot") version "4.1.0"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.graalvm.buildtools.native") version "0.11.5"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
}

group = "pl.delukesoft"
version = "0.0.1-SNAPSHOT"

object Versions {
  const val liquibase = "5.0.3"
  const val springdocOpenApi = "3.0.3"
  const val cucumber = "7.22.1"
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

kotlin {
  jvmToolchain(25)
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_25)
    freeCompilerArgs.addAll(
      "-Xjsr305=strict",
      "-Xannotation-default-target=param-property",
    )
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

graalvmNative {
  agent {
    defaultMode.set("standard")
  }
  binaries {
    all {
      resources.autodetect()
    }
    named("main") {
      buildArgs("-march=compatibility")
    }
  }
  metadataRepository {
    enabled.set(true)
  }
}

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  implementation("pl.delukesoft:authplugin:1.1")
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.liquibase:liquibase-core:${Versions.liquibase}")
  implementation("org.liquibase.ext:liquibase-mongodb:${Versions.liquibase}")
  implementation("org.springframework.boot:spring-boot-starter-webmvc")
  implementation("org.springframework.boot:spring-boot-starter-aspectj")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-cache")
  implementation("com.github.ben-manes.caffeine:caffeine")
  implementation("org.springframework.boot:spring-boot-jackson2")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.springdocOpenApi}")
  testImplementation("io.cucumber:cucumber-spring:${Versions.cucumber}")
  testImplementation("io.cucumber:cucumber-java:${Versions.cucumber}")
  testImplementation("io.cucumber:cucumber-junit-platform-engine:${Versions.cucumber}")
  testImplementation("org.junit.platform:junit-platform-suite")
  testImplementation("io.cucumber:cucumber-java8:${Versions.cucumber}")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.testcontainers:testcontainers-junit-jupiter")
  testImplementation("org.testcontainers:testcontainers-mongodb")
  testImplementation("io.mockk:mockk:1.14.11")
  testImplementation("net.java.dev.jna:jna:5.13.0")
  testImplementation("net.java.dev.jna:jna-platform:5.13.0")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
  jvmArgs(
    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
  )
}

tasks.named("processTestAot") {
  enabled = false
}