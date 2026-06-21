package de.derfrzocker.anime.calendar.server.integration.syoboi.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;

public final class TrackingChannelNotificationExceptions {

    private static final String RESOURCE_NAME = "TrackingChannelNotification";

    public static Supplier<ResourceNotFoundException> notFound(@Nullable NotificationId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(@Nullable NotificationId id) {
        return InconsistentDataException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }

    public static Supplier<AlreadyCreatedException> alreadyCreated(@Nullable NotificationId id) {
        return AlreadyCreatedException.with(unwrapSafe(id, NotificationId::raw), RESOURCE_NAME);
    }
}
