FROM openjdk:17

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV PROFILE prod

ENTRYPOINT java -jar app.jar --spring.profiles.active="${PROFILE}"