package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record Channel(ChannelId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy,
                      String name) implements ModificationInfo {

    public static Channel from(ChannelId id, ChannelCreateData createData, RequestContext context) {
        return new Channel(id,
                           context.requestTime(),
                           context.requestUser(),
                           context.requestTime(),
                           context.requestUser(),
                           createData.name());
    }

    public Channel updateWithData(ChannelUpdateData updateData, RequestContext context) {
        return new Channel(id(),
                           createdAt(),
                           createdBy(),
                           context.requestTime(),
                           context.requestUser(),
                           updateData.name().apply(name()));
    }
}
