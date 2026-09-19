package de.derfrzocker.anime.calendar.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionUpdateData;

import java.util.Optional;

public interface ManualLinkNotificationActionService {

    Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    ManualLinkNotificationAction createWithData(NotificationActionId id,
                                                ManualLinkNotificationActionCreateData createData,
                                                RequestContext context);

    ManualLinkNotificationAction updateWithData(NotificationActionId id,
                                                ManualLinkNotificationActionUpdateData updateData,
                                                RequestContext context);
}
