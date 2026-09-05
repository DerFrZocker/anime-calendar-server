package de.derfrzocker.anime.calendar.anime.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationCreateData;
import java.util.Optional;

public interface NewAnimeNotificationService {

    Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context);

    NewAnimeNotification createWithData(
            NotificationId id,
            NewAnimeNotificationCreateData createData,
            RequestContext context);
}
