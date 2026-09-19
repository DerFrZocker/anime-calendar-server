package de.derfrzocker.anime.calendar.metadata.mongodb.model;

import de.derfrzocker.anime.calendar.actor.ActorId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;
import java.util.Objects;

public record AuditMetadataDO(
        @BsonProperty("created_at") Instant createdAt,
        @BsonProperty("created_by") ActorId createdBy,
        @BsonProperty("last_modified_at") Instant lastModifiedAt,
        @BsonProperty("last_modified_by") ActorId lastModifiedBy) {

    public AuditMetadataDO {
        Objects.requireNonNull(createdAt);
        Objects.requireNonNull(createdBy);
        Objects.requireNonNull(lastModifiedAt);
        Objects.requireNonNull(lastModifiedBy);
    }
}
