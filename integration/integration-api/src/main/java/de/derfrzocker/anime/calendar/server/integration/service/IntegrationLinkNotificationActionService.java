package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface IntegrationLinkNotificationActionService {

    Stream<IntegrationLinkNotificationAction> getAll(RequestContext context);

    Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    IntegrationLinkNotificationAction createWithData(NotificationActionId id,
                                                     IntegrationLinkNotificationActionCreateData createData,
                                                     RequestContext context);

    IntegrationLinkNotificationAction updateWithData(NotificationActionId id,
                                                     IntegrationLinkNotificationActionUpdateData updateData,
                                                     RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
