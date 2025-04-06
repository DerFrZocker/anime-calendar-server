package de.derfrzocker.anime.calendar.server.integration.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface ManualLinkNotificationActionService {

    Stream<ManualLinkNotificationAction> getAll(RequestContext context);

    Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context);

    ManualLinkNotificationAction createWithData(NotificationActionId id,
                                                ManualLinkNotificationActionCreateData createData,
                                                RequestContext context);

    ManualLinkNotificationAction updateWithData(NotificationActionId id,
                                                ManualLinkNotificationActionUpdateData updateData,
                                                RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
