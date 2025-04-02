FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/swiftpay-scheduler-0.0.1-SNAPSHOT.jar swiftpay-scheduler.jar
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "swiftpay-scheduler.jar"]
