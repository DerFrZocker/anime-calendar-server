FROM maven:3.9.5-eclipse-temurin-21-alpine AS builder
WORKDIR /usr/src

COPY ./ ./

RUN mvn package -DskipTests=true

FROM builder AS tester

RUN mvn verify

FROM eclipse-temurin:21-jre-alpine

RUN mkdir -p /anime-calendar

COPY --chown=185 --from=builder /usr/src/target/quarkus-app/lib/ /anime-calendar/lib/
COPY --chown=185 --from=builder /usr/src/target/quarkus-app/*.jar /anime-calendar/
COPY --chown=185 --from=builder /usr/src/target/quarkus-app/app/ /anime-calendar/app/
COPY --chown=185 --from=builder /usr/src/target/quarkus-app/quarkus/ /anime-calendar/quarkus/

USER 185

WORKDIR /anime-calendar

ENTRYPOINT ["java","-jar","quarkus-run.jar"]
