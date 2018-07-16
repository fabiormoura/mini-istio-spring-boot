FROM openjdk:8u171-jre-alpine3.8

ENV SERVER_PORT=8080

RUN apk add --no-cache --update curl

ADD target/*.jar /app/service.jar


CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/service.jar"]