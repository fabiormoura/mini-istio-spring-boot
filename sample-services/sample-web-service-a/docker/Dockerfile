FROM openjdk:8u171-jre-alpine3.8

ADD target/*.jar /app/service.jar

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/service.jar"]