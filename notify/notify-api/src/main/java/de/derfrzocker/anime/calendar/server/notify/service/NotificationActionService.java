package de.derfrzocker.anime.calendar.server.notify.service;

import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface NotificationActionService {

    Stream<NotificationAction> getAll(RequestContext context);

    Stream<NotificationAction> getAllWithData(NotificationId notificationId, RequestContext context);

    Optional<NotificationAction> getById(NotificationActionId id, RequestContext context);

    NotificationAction createWithData(NotificationActionCreateData createData, RequestContext context);

    NotificationAction updateWithData(NotificationActionId id,
                                      NotificationActionUpdateData updateData,
                                      RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
