FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} absbanksvc.jar

ENTRYPOINT ["java", "-jar", "/absbanksvc.jar"]

EXPOSE 8081