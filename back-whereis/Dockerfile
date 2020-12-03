#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -DskipTests

#
# Package stage
#
FROM openjdk:8-jdk-alpine
COPY --from=build /home/app/target/whereis-0.0.1-SNAPSHOT.jar /usr/local/lib/whereis.jar
EXPOSE 9080
ENTRYPOINT ["java","-jar","/usr/local/lib/whereis.jar"]