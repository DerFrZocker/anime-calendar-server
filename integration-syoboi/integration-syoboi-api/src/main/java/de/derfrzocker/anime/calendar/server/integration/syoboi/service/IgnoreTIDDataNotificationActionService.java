package de.derfrzocker.anime.calendar.server.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface IgnoreTIDDataNotificationActionService {

    Stream<IgnoreTIDDataNotificationAction> getAll(RequestContext context);

    Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context);

    IgnoreTIDDataNotificationAction createWithData(NotificationActionId id,
                                                   IgnoreTIDDataNotificationActionCreateData createData,
                                                   RequestContext context);

    IgnoreTIDDataNotificationAction updateWithData(NotificationActionId id,
                                                   IgnoreTIDDataNotificationActionUpdateData updateData,
                                                   RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
