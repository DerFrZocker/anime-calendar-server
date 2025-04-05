package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record IgnoreTIDDataNotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy,
                                              Instant updatedAt, UserId updatedBy,
                                              TID tid) implements ModificationInfo {

    public static IgnoreTIDDataNotificationAction from(NotificationActionId id,
                                                       IgnoreTIDDataNotificationActionCreateData createData,
                                                       RequestContext context) {
        return new IgnoreTIDDataNotificationAction(id,
                                                   context.requestTime(),
                                                   context.requestUser(),
                                                   context.requestTime(),
                                                   context.requestUser(),
                                                   createData.tid());
    }

    public IgnoreTIDDataNotificationAction updateWithData(IgnoreTIDDataNotificationActionUpdateData updateData,
                                                          RequestContext context) {
        return new IgnoreTIDDataNotificationAction(id(),
                                                   createdAt(),
                                                   createdBy(),
                                                   context.requestTime(),
                                                   context.requestUser(),
                                                   tid());
    }
}
