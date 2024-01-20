FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/assurapp-1.0.0.jar /app/assurapp.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "assurapp.jar"]
