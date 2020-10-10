#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/watch-eshop-0.0.1-SNAPSHOT.jar /usr/local/lib/watch-eshop.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/watch-eshop.jar"]