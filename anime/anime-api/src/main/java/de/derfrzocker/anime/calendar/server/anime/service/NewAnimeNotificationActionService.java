package de.derfrzocker.anime.calendar.server.anime.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionUpdateData;
import java.util.Optional;
import java.util.stream.Stream;

public interface NewAnimeNotificationActionService {

    Stream<NewAnimeNotificationAction> getAll(RequestContext context);

    Optional<NewAnimeNotificationAction> getById(NotificationActionId id, RequestContext context);

    NewAnimeNotificationAction createWithData(NotificationActionId id,
                                              NewAnimeNotificationActionCreateData createData,
                                              RequestContext context);

    NewAnimeNotificationAction updateWithData(NotificationActionId id,
                                              NewAnimeNotificationActionUpdateData updateData,
                                              RequestContext context);

    void deleteById(NotificationActionId id, RequestContext context);
}
