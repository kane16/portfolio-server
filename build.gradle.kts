import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.5"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "pl.delukesoft"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
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
  mavenCentral()
}

extra["springCloudVersion"] = "2024.0.1"

object Versions {
  const val cucumber = "7.22.1"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  testImplementation("io.cucumber:cucumber-spring:${Versions.cucumber}")
  testImplementation("io.cucumber:cucumber-java:${Versions.cucumber}")
  testImplementation("io.cucumber:cucumber-junit:${Versions.cucumber}")
  testImplementation("io.cucumber:cucumber-java8:${Versions.cucumber}")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:mongodb")
  testImplementation("io.mockk:mockk:1.13.2")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
  }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
