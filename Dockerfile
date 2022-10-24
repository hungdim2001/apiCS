# syntax=docker/dockerfile:1
FROM eclipse-temurin:19-jdk
#working directory
WORKDIR /app
#copy from host to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#run this inside  the image
RUN ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
