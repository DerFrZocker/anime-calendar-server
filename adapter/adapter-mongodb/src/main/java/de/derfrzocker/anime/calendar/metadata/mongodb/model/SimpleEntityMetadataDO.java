package de.derfrzocker.anime.calendar.metadata.mongodb.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

public record SimpleEntityMetadataDO(@BsonProperty("audit") AuditMetadataDO audit) {

    public SimpleEntityMetadataDO {
        Objects.requireNonNull(audit);
    }
}
