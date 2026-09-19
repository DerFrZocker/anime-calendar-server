package de.derfrzocker.anime.calendar.integration.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionUpdateData;

import java.util.Optional;

public interface StreamingNotificationActionService {

    Optional<StreamingNotificationAction> getById(NotificationActionId id, RequestContext context);

    StreamingNotificationAction createWithData(NotificationActionId id,
                                               StreamingNotificationActionCreateData createData,
                                               RequestContext context);

    StreamingNotificationAction updateWithData(NotificationActionId id,
                                               StreamingNotificationActionUpdateData updateData,
                                               RequestContext context);
}
