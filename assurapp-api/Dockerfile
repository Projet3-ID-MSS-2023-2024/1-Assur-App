FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/assurapp-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]