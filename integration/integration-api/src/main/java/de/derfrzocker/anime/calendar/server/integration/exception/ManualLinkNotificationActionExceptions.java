package de.derfrzocker.anime.calendar.server.integration.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.AlreadyCreatedException;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import java.util.function.Supplier;

public final class ManualLinkNotificationActionExceptions {

    private static final String RESOURCE_NAME = "ManualLinkNotificationAction";

    private ManualLinkNotificationActionExceptions() {
    }

    public static Supplier<ResourceNotFoundException> notFound(NotificationActionId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(NotificationActionId id) {
        return InconsistentDataException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }

    public static Supplier<AlreadyCreatedException> alreadyCreated(NotificationActionId id) {
        return AlreadyCreatedException.with(unwrapSafe(id, NotificationActionId::raw), RESOURCE_NAME);
    }
}
