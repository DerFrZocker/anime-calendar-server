package de.derfrzocker.anime.calendar.metadata;

import de.derfrzocker.anime.calendar.RequestContext;

public record SimpleEntityMetadata(AuditMetadata audit) implements EntityMetadata {

    public static SimpleEntityMetadata from(RequestContext context) {
        return new SimpleEntityMetadata(AuditMetadata.from(context));
    }

    public SimpleEntityMetadata updateWithData(RequestContext context) {
        return new SimpleEntityMetadata(audit.updateWithData(context));
    }
}
