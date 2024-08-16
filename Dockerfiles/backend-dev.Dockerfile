FROM openjdk:17-jdk-slim-buster AS builder

WORKDIR /backend

#COPY . .

ARG JAR_FILE_PATH=/backend/build/libs/*.jar

COPY ${JAR_FILE_PATH} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#COPY . .
#
#CMD ["./gradlew", "clean", "build"]
#
#ARG JAR_FILE_PATH=/build/libs/*.jar
#
#COPY ${JAR_FILE_PATH} app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]