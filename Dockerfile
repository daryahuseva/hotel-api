FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/hotel-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "app.jar"]