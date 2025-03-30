package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record Notification(NotificationId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy,
                           NotificationType type, Instant validUntil) implements ModificationInfo {

    public static Notification from(NotificationId id, NotificationCreateData createData, RequestContext context) {
        return new Notification(id,
                                context.requestTime(),
                                context.requestUser(),
                                context.requestTime(),
                                context.requestUser(),
                                createData.type(),
                                createData.validUntil());
    }

    public Notification updateWithData(NotificationUpdateData updateData, RequestContext context) {
        return new Notification(id(),
                                createdAt(),
                                createdBy(),
                                context.requestTime(),
                                context.requestUser(),
                                type(),
                                validUntil());
    }
}
