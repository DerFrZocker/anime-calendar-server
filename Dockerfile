FROM maven:3.9.16-eclipse-temurin-25-alpine AS builder
WORKDIR /usr/src

COPY ./ ./

RUN mvn package -DskipTests=true -B

FROM builder AS tester

RUN mvn verify -B

FROM eclipse-temurin:25-jre-alpine

RUN mkdir -p /anime-calendar

COPY --chown=185 --from=builder /usr/src/anime-calendar-server/target/quarkus-app/lib/ /anime-calendar/lib/
COPY --chown=185 --from=builder /usr/src/anime-calendar-server/target/quarkus-app/*.jar /anime-calendar/
COPY --chown=185 --from=builder /usr/src/anime-calendar-server/target/quarkus-app/app/ /anime-calendar/app/
COPY --chown=185 --from=builder /usr/src/anime-calendar-server/target/quarkus-app/quarkus/ /anime-calendar/quarkus/

USER 185

WORKDIR /anime-calendar

ENTRYPOINT ["java","-jar","quarkus-run.jar"]
