package de.derfrzocker.anime.calendar.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationActionCreateData;

import java.util.Optional;

public interface IntegrationLinkNotificationActionService {

    Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    IntegrationLinkNotificationAction createWithData(NotificationActionId id,
                                                     IntegrationLinkNotificationActionCreateData createData,
                                                     RequestContext context);
}
