FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./assurapp-api/target/assurapp-1.0.0.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]
