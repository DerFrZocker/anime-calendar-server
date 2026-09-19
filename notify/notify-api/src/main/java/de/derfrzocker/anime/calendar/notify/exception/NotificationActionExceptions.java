package de.derfrzocker.anime.calendar.notify.exception;

import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;

import java.util.function.Supplier;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;

public final class NotificationActionExceptions {

    private static final String RESOURCE_NAME = "NotificationAction";

    public static Supplier<ResourceNotFoundException> notFound(NotificationActionId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }
}
