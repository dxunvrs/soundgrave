FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build

COPY . .
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /build/build/libs/soundgrave-1.0.jar app.jar

ENV SERVER_PORT=1220

EXPOSE 1220

ENTRYPOINT ["java", "-jar", "app.jar"]