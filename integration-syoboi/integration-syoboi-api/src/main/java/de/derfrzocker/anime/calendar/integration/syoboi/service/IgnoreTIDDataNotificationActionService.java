package de.derfrzocker.anime.calendar.integration.syoboi.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;

import java.util.Optional;

public interface IgnoreTIDDataNotificationActionService {

    Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context);

    IgnoreTIDDataNotificationAction createWithData(NotificationActionId id,
                                                   IgnoreTIDDataNotificationActionCreateData createData,
                                                   RequestContext context);
}
