FROM eclipse-temurin:21-jre-jammy

LABEL org.opencontainers.image.title="ShortLink"
LABEL org.opencontainers.image.description="Runtime image for the ShortLink Spring Boot application"

WORKDIR /app
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

