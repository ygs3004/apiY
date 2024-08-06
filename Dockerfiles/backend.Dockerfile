FROM openjdk:17-jdk-slim-buster AS builder

COPY . .

WORKDIR /backend

RUN chmod +x ./gradlew

RUN ./gradlew bootJar

FROM openjdk:17-jdk-slim-buster

COPY --from=builder /backend/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

#COPY . .
#
#CMD ["./gradlew", "clean", "build"]
#
#ARG JAR_FILE_PATH=/build/libs/*.jar
#
#COPY ${JAR_FILE_PATH} app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]