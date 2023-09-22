# Этап 1 - Сборка проекта в jar
FROM maven:3.6.3-openjdk-17 AS maven
WORKDIR /app
COPY . /app
RUN mvn install


# Этап 2 - Копирование jar в другой image
FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY --from=maven /app/target/main.jar app.jar
CMD ["java", "-jar", "app.jar"]