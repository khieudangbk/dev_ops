# --- Bước 1: Build (Sử dụng Maven để đóng gói file JAR) ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy file cấu hình và code vào container
COPY pom.xml .
COPY src ./src

# Chạy lệnh build của Maven
RUN mvn clean package -DskipTests

# --- Bước 2: Chạy ứng dụng (Sử dụng JRE nhẹ để chạy file JAR) ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# COPY file JAR đã build từ stage 'builder' sang stage này
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# ENTRYPOINT [ "java", "-javaagent:/otel/opentelemetry-javaagent.jar", "-Dotel.service.name=inventory-service", "-Dotel.exporter.otlp.endpoint=http://tempo:4317", "-Dotel.traces.exporter=otlp", "-Dotel.metrics.exporter=none", "-jar", "app.jar"]