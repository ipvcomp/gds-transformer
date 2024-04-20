FROM openjdk:8-jdk-alpine
MAINTAINER ipurvey.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
