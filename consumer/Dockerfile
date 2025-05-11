FROM openjdk:17
ARG JAR_NAME
COPY target/${JAR_NAME} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
