package de.derfrzocker.anime.calendar.server.integration.syoboi.exception;

import static de.derfrzocker.anime.calendar.server.model.domain.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import java.util.function.Supplier;

public final class TrackingChannelNotificationActionExceptions {

    private static final String RESOURCE_NAME = "TrackingChannelNotificationAction";

    public static Supplier<ResourceNotFoundException> notFound(NotificationActionId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(NotificationActionId id) {
        return InconsistentDataException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }
}
