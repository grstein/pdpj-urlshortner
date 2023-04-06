# Stage 1: Compile the application
FROM docker.io/openjdk:20-jdk-slim AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

VOLUME /root/.m2

# Add settings.xml to mount .m2 folder on host
COPY settings.xml /root/.m2/settings.xml

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM docker.io/openjdk:20-jre-slim

WORKDIR /app

COPY --from=build /app/target/url-shortener-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "url-shortener-0.0.1-SNAPSHOT.jar"]

