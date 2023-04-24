FROM maven:3.9.1 AS build

ARG EMPLOYEE_SERVICE=http://localhost:8080/api
ARG PATIENTS_SERVICE=http://localhost:8081/api

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:11

COPY --from=build /usr/src/app/target/laboratory-0.0.1-SNAPSHOT.jar /usr/app/laboratory-0.0.1-SNAPSHOT.jar
EXPOSE 8083
CMD ["java", "-jar", "-Demployee-url=${EMPLOYEE_SERVICE}", "-Dpatiets-url=${PATIENTS_SERVICE}","/usr/app/laboratory-0.0.1-SNAPSHOT.jar"]
