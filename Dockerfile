FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} conference.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "conference.jar"]