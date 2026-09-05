package de.derfrzocker.anime.calendar.anime.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotification;
import java.util.Optional;

public interface NewAnimeNotificationDao {

    Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context);

    void create(NewAnimeNotification notification, RequestContext context);
}
