FROM openjdk:16-alpine3.13
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} client-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/client-service.jar"]
