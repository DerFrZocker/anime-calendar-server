package de.derfrzocker.anime.calendar.server.anime.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import java.util.Optional;
import java.util.stream.Stream;

public interface NewAnimeNotificationActionDao {

    Stream<NewAnimeNotificationAction> getAll(RequestContext context);

    Optional<NewAnimeNotificationAction> getById(NotificationActionId id, RequestContext context);

    void create(NewAnimeNotificationAction anime, RequestContext context);

    void update(NewAnimeNotificationAction anime, RequestContext context);

    void delete(NewAnimeNotificationAction anime, RequestContext context);
}
