package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record NotificationAction(NotificationActionId id, Instant createdAt, UserId createdBy, Instant updatedAt,
                                 UserId updatedBy, NotificationId notificationId, NotificationActionType actionType,
                                 Instant executedAt, UserId executedBy) implements ModificationInfo {

    public static NotificationAction from(NotificationActionId id,
                                          NotificationActionCreateData createData,
                                          RequestContext context) {
        return new NotificationAction(id,
                                      context.requestTime(),
                                      context.requestUser(),
                                      context.requestTime(),
                                      context.requestUser(),
                                      createData.notificationId(),
                                      createData.actionType(),
                                      null,
                                      null);
    }

    public NotificationAction updateWithData(NotificationActionUpdateData updateData, RequestContext context) {
        return new NotificationAction(id(),
                                      createdAt(),
                                      createdBy(),
                                      context.requestTime(),
                                      context.requestUser(),
                                      notificationId(),
                                      actionType(),
                                      updateData.executedAt().apply(executedAt()),
                                      updateData.executedBy().apply(executedBy()));
    }
}
