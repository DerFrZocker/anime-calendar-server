package de.derfrzocker.anime.calendar.server.integration.notify.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import java.util.function.Supplier;

public final class StreamingNotificationExceptions {

    private static final String RESOURCE_NAME = "StreamingNotification";

    private StreamingNotificationExceptions() {
    }

    public static Supplier<ResourceNotFoundException> notFound(NotificationId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(NotificationId id) {
        return InconsistentDataException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }

    public static Supplier<AlreadyCreatedException> alreadyCreated(NotificationId id) {
        return AlreadyCreatedException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }
}
