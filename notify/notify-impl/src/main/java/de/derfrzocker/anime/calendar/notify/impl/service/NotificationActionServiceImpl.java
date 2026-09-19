package de.derfrzocker.anime.calendar.notify.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.notify.api.NotificationActionUpdateData;
import de.derfrzocker.anime.calendar.notify.dao.NotificationActionDao;
import de.derfrzocker.anime.calendar.notify.impl.generator.NotificationActionIdGenerator;
import de.derfrzocker.anime.calendar.notify.service.NotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.stream.Stream;

import static de.derfrzocker.anime.calendar.notify.exception.NotificationActionExceptions.notFound;

@ApplicationScoped
public class NotificationActionServiceImpl implements NotificationActionService {

    @Inject
    NotificationActionDao dao;
    @Inject
    NotificationActionIdGenerator idGenerator;

    @Override
    public Stream<NotificationAction> getAllWithData(NotificationId notificationId, RequestContext context) {
        return this.dao.getAllWithData(notificationId, context);
    }

    @Override
    public Optional<NotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public NotificationAction createWithData(NotificationActionCreateData createData, RequestContext context) {
        NotificationActionId id = this.idGenerator.generateId(potential -> getById(potential, context).isPresent());
        NotificationAction notificationAction = NotificationAction.from(id, createData, context);

        this.dao.create(notificationAction, context);

        return notificationAction;
    }

    @Override
    public NotificationAction updateWithData(NotificationActionId id,
                                             NotificationActionUpdateData updateData,
                                             RequestContext context) {
        NotificationAction current = getById(id, context).orElseThrow(notFound(id));
        NotificationAction updated = current.updateWithData(updateData, context);

        this.dao.update(updated, context);

        return updated;
    }
}
