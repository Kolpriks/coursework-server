# Этап сборки
FROM gradle:8.7-jdk21-alpine AS builder
WORKDIR /app

# Копируем исходный код
COPY src ./src
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Собираем JAR
RUN gradle clean bootJar --no-daemon

# Этап запуска
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Копируем собранный JAR из этапа сборки
COPY --from=builder /app/build/libs/*.jar app.jar

# Настройки окружения
ENV TZ=Europe/Moscow
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
