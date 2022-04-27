FROM openjdk:8-jdk-alpine
ARG JAR_FILE=assignment-0.0.1-SNAPSHOT.jar
ADD src/main/resources/application.yml application.yml
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
