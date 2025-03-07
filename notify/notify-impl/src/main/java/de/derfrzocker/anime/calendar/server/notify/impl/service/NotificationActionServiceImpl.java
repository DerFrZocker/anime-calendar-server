package de.derfrzocker.anime.calendar.server.notify.impl.service;

import static de.derfrzocker.anime.calendar.server.notify.exception.NotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.notify.dao.NotificationActionDao;
import de.derfrzocker.anime.calendar.server.notify.impl.generator.NotificationActionIdGenerator;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class NotificationActionServiceImpl implements NotificationActionService {

    @Inject
    NotificationActionDao dao;
    @Inject
    NotificationActionIdGenerator idGenerator;
    @Inject
    NotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<NotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

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

        this.eventPublisher.firePreCreate(notificationAction, createData, context);
        this.dao.create(notificationAction, context);
        this.eventPublisher.firePostCreate(notificationAction, createData, context);

        return notificationAction;
    }

    @Override
    public NotificationAction updateWithData(NotificationActionId id,
                                             NotificationActionUpdateData updateData,
                                             RequestContext context) {
        NotificationAction current = getById(id, context).orElseThrow(notFound(id));
        NotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return updated;
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        NotificationAction notificationAction = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(notificationAction, context);
        this.dao.delete(notificationAction, context);
        this.eventPublisher.firePostDelete(notificationAction, context);
    }
}
