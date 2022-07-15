# Build stage with maven, that's generates our jar
FROM maven:3.8.5-jdk-11 AS Build
ADD . /api-signature-doc
WORKDIR /api-signature-doc
RUN mvn clean package spring-boot:repackage

# Start with a base image containing Java runtime
FROM openjdk:11

# Make port 8080 available to the world outside this container
EXPOSE 8080

COPY --from=build /api-signature-doc/target/api-signature-doc-0.0.1-SNAPSHOT.jar api-signature-doc.jar


# Run the jar file
ENTRYPOINT ["java","-jar","api-signature-doc.jar"]