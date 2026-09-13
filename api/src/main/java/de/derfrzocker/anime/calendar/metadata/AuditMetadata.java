package de.derfrzocker.anime.calendar.metadata;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.actor.ActorId;

import java.time.Instant;

public record AuditMetadata(Instant createdAt, ActorId createdBy, Instant lastModifiedAt, ActorId lastModifiedBy) {

    public static AuditMetadata from(RequestContext context) {
        return new AuditMetadata(
                context.requestTime(),
                context.requestActor(),
                context.requestTime(),
                context.requestActor());
    }

    public AuditMetadata updateWithData(RequestContext context) {
        return new AuditMetadata(createdAt(), createdBy(), context.requestTime(), context.requestActor());
    }
}
