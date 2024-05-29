FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
