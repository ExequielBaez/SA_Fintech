FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/fintech-0.0.1.jar
COPY ${JAR_FILE} app_fintech.jar
EXPOSE 9698
ENTRYPOINT ["java", "-jar", "app_fintech.jar", "--spring.profiles.active=docker"]