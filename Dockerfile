# syntax=docker/dockerfile:1.4
FROM eclipse-temurin:17-jdk AS authplugin-build

WORKDIR /authplugin

COPY --from=authplugin gradle/ gradle/
COPY --from=authplugin gradlew build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

COPY --from=authplugin src/ src/

RUN ./gradlew publishToMavenLocal --no-daemon -x test

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY --from=authplugin-build /root/.m2 /root/.m2

COPY gradle/ gradle/
COPY gradlew build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

COPY src/ src/

RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
