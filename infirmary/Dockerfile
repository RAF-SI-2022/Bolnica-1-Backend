FROM maven:3.9.1 AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:11

COPY --from=build /usr/src/app/target/infirmary-0.0.1-SNAPSHOT.jar /usr/app/infirmary-0.0.1-SNAPSHOT.jar
EXPOSE 8084
CMD ["java", "-jar", "/usr/app/infirmary-0.0.1-SNAPSHOT.jar"]
