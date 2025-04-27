package de.derfrzocker.anime.calendar.server.integration.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface StreamingNotificationService {

    Stream<StreamingNotification> getAll(RequestContext context);

    Optional<StreamingNotification> getById(NotificationId id, RequestContext context);

    StreamingNotification createWithData(NotificationId id,
                                         StreamingNotificationCreateData createData,
                                         RequestContext context);

    StreamingNotification updateWithData(NotificationId id,
                                         StreamingNotificationUpdateData updateData,
                                         RequestContext context);

    void deleteById(NotificationId id, RequestContext context);
}
