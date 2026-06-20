package de.derfrzocker.anime.calendar.server.anime.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationCreateData;
import java.util.Optional;

public interface NewAnimeNotificationService {

    Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context);

    NewAnimeNotification createWithData(
            NotificationId id,
            NewAnimeNotificationCreateData createData,
            RequestContext context);
}
