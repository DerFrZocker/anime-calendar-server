package de.derfrzocker.anime.calendar.server.anime.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import java.util.Optional;

public interface NewAnimeNotificationDao {

    Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context);

    void create(NewAnimeNotification notification, RequestContext context);
}
