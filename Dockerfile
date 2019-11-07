FROM openjdk:8-jdk-alpine

ENV EXPOSED_PORT 8080

ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} demo.jar

EXPOSE $EXPOSED_PORT

ENTRYPOINT ["java","-jar","/demo.jar"]
