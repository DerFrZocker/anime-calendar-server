package de.derfrzocker.anime.calendar.metadata;

import de.derfrzocker.anime.calendar.RequestContext;

import java.util.Objects;

public record SimpleEntityMetadata(AuditMetadata audit) implements EntityMetadata {

    public SimpleEntityMetadata {
        Objects.requireNonNull(audit);
    }

    public static SimpleEntityMetadata from(RequestContext context) {
        return new SimpleEntityMetadata(AuditMetadata.from(context));
    }

    public SimpleEntityMetadata updateWith(RequestContext context) {
        return new SimpleEntityMetadata(audit.updateWith(context));
    }
}
