package de.derfrzocker.anime.calendar.server.anime.impl.service;

import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.anime.dao.NewAnimeNotificationActionDao;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class NewAnimeNotificationActionServiceImpl implements NewAnimeNotificationActionService {

    @Inject
    NewAnimeNotificationActionDao dao;
    @Inject
    NewAnimeNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<NewAnimeNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

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

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public NewAnimeNotificationAction updateWithData(NotificationActionId id,
                                                     NewAnimeNotificationActionUpdateData updateData,
                                                     RequestContext context) {
        NewAnimeNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        NewAnimeNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        NewAnimeNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
