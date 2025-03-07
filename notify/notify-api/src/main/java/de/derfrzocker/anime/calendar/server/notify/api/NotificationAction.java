package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.ModificationInfo;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
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
