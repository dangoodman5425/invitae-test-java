FROM maven:3.6.0-jdk-11-slim AS builder
WORKDIR /workspace
COPY . .
RUN mvn clean package spring-boot:repackage

FROM openjdk:11-jre-slim
ARG uid=1001
ARG user=interviewee

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid ${uid} ${user}

COPY --chown=${user}:${user} --from=builder /workspace/invitae-variant-api/target/invitae-variant-api-*.jar exercise.jar

EXPOSE 8080

USER ${uid}

CMD ["java","-jar","/exercise.jar"]
