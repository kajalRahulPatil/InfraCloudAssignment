# -------------------------------
# Stage 1 : Build using Maven 3.9.11 + Java 21
# -------------------------------
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
# Copy pom first (cache dependency layer)
COPY pom.xml .
# Copy source code
COPY src ./src

# Build application
RUN mvn -f ./pom.xml clean package 


# -------------------------------
# Stage 2 : Runtime Image (Java 21)
# -------------------------------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]