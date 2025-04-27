package de.derfrzocker.anime.calendar.server.integration.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface StreamingNotificationActionService {

    Stream<StreamingNotificationAction> getAll(RequestContext context);

    Optional<StreamingNotificationAction> getById(NotificationActionId id, RequestContext context);

    StreamingNotificationAction createWithData(NotificationActionId id,
                                               StreamingNotificationActionCreateData createData,
                                               RequestContext context);

    StreamingNotificationAction updateWithData(NotificationActionId id,
                                               StreamingNotificationActionUpdateData updateData,
                                               RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
