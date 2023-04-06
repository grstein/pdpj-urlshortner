# Stage 1: Compile the application
FROM docker.io/eclipse-temurin:17-jdk-alpine AS build

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
FROM docker.io/eclipse-temurin:17-jre-alpine

WORKDIR /app
                  
COPY --from=build /app/target/urlshortener-0.0.1-SNAPSHOT.war .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "urlshortener-0.0.1-SNAPSHOT.war"]

