FROM openjdk:24-jdk-slim
LABEL authors="Егор"

VOLUME /tmp

COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]