package de.derfrzocker.anime.calendar.server.anime.impl.service;

import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationCreateData;
import de.derfrzocker.anime.calendar.server.anime.dao.NewAnimeNotificationDao;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class NewAnimeNotificationServiceImpl implements NewAnimeNotificationService {

    @Inject
    NewAnimeNotificationDao dao;

    @Override
    public Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public NewAnimeNotification createWithData(
            NotificationId id,
            NewAnimeNotificationCreateData createData,
            RequestContext context) {

        Optional<NewAnimeNotification> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        NewAnimeNotification action = NewAnimeNotification.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
