package de.derfrzocker.anime.calendar.server.notify.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import java.util.function.Supplier;

public final class NotificationExceptions {

    private static final String RESOURCE_NAME = "Notification";

    public static Supplier<ResourceNotFoundException> notFound(NotificationId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(NotificationId id) {
        return InconsistentDataException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }
}
