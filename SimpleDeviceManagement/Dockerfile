### STAGE 1: BUILD
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

### STAGE 2: RUNTIME
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/SimpleDeviceManagementApp/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
