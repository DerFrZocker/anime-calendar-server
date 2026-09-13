package de.derfrzocker.anime.calendar.notify.api;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.notify.NotificationType;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record Notification<T>(
        NotificationId id, Instant createdAt, UserId createdBy, Instant updatedAt, UserId updatedBy,
        NotificationType type, Instant validUntil, T data) implements ModificationInfo {

    public static <T> Notification<T> from(
            NotificationId id,
            NotificationCreateData<T> createData,
            RequestContext context) {
        return new Notification<>(
                id,
                                context.requestTime(),
                                context.requestUser(),
                                context.requestTime(),
                                context.requestUser(),
                                createData.type(),
                createData.validUntil(),
                createData.data());
    }
}
