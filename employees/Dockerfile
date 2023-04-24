FROM harbor.k8s.elab.rs/docker-hub/library/maven:3.9.1 AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM harbor.k8s.elab.rs/docker-hub/library/openjdk:11

COPY --from=build /usr/src/app/target/employees-0.0.1-SNAPSHOT.jar /usr/app/employees-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/usr/app/employees-0.0.1-SNAPSHOT.jar"]