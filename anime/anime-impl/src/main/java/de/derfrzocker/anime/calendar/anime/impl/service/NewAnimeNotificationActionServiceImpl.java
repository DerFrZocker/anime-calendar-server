package de.derfrzocker.anime.calendar.anime.impl.service;

import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.anime.api.NewAnimeNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.anime.dao.NewAnimeNotificationActionDao;
import de.derfrzocker.anime.calendar.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.anime.exception.NewAnimeNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.anime.exception.NewAnimeNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.anime.exception.NewAnimeNotificationActionExceptions.notFound;

@ApplicationScoped
public class NewAnimeNotificationActionServiceImpl implements NewAnimeNotificationActionService {

    @Inject
    NewAnimeNotificationActionDao dao;

    @Override
    public Optional<NewAnimeNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public NewAnimeNotificationAction createWithData(NotificationActionId id,
                                                     NewAnimeNotificationActionCreateData createData,
                                                     RequestContext context) {

        Optional<NewAnimeNotificationAction> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        NewAnimeNotificationAction action = NewAnimeNotificationAction.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public NewAnimeNotificationAction updateWithData(NotificationActionId id,
                                                     NewAnimeNotificationActionUpdateData updateData,
                                                     RequestContext context) {
        NewAnimeNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        NewAnimeNotificationAction updated = current.updateWithData(updateData, context);

        this.dao.update(updated, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
