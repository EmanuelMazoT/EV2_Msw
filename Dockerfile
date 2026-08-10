# Build stage: compile the project with Maven on Java 25 in the image maven image.
# The app reads all database credentials from environment variables (DB_URL,
# DB_USERNAME, DB_PASSWORD) — nothing is baked into the image.
FROM --platform=linux/amd64 maven:3.9.16-eclipse-temurin-25-noble AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage: minimal JRE for Java 25.
FROM --platform=linux/amd64 eclipse-temurin:25-jre-noble
WORKDIR /app

COPY --from=build /app/target/evaluaciont2-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]