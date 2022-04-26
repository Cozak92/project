FROM openjdk:8-jdk-alpine
ARG IDLE_PROFILE
ARG JAR_FILE=*SNAPSHOT.jar
COPY ${JAR_FILE} /bulid/libs/*SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#,"--spring.profiles.active=${ENV_IDLE_PROFILE}"
