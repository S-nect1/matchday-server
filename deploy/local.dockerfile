# Build stage

FROM gradle:8.13.0-jdk17-ubi-minimal AS builder
WORKDIR /app

COPY . .

RUN ./gradlew build
#RUN ./gradlew build -x test

# Run stage

FROM openjdk:17-slim
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV TZ=Asia/Seoul
ENV SPRING_PROFILES_ACTIVE=local

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]