FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder
WORKDIR /usr/src

COPY ./ ./

RUN mvn package -DskipTests=true

FROM builder AS tester

RUN mvn verify

FROM eclipse-temurin:21-jre-alpine

RUN mkdir -p /anime-calendar

COPY --from=builder --chown=185 target/quarkus-app/lib/ /anime-calendar/lib/
COPY --from=builder --chown=185 target/quarkus-app/*.jar /anime-calendar/
COPY --from=builder --chown=185 target/quarkus-app/app/ /anime-calendar/app/
COPY --from=builder --chown=185 target/quarkus-app/quarkus/ /anime-calendar/quarkus/

USER 185

WORKDIR /anime-calendar

ENTRYPOINT ["java","-jar","quarkus-run.jar"]
