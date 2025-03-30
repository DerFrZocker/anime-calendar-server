package de.derfrzocker.anime.calendar.server.notify.impl.service;

import static de.derfrzocker.anime.calendar.server.notify.exception.NotificationExceptions.notFound;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationUpdateData;
import de.derfrzocker.anime.calendar.server.notify.dao.NotificationDao;
import de.derfrzocker.anime.calendar.server.notify.impl.generator.NotificationIdGenerator;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class NotificationServiceImpl implements NotificationService {

    @Inject
    NotificationDao dao;
    @Inject
    NotificationIdGenerator idGenerator;
    @Inject
    NotificationEventPublisher eventPublisher;

    @Override
    public Stream<Notification> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<Notification> getById(NotificationId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Notification createWithData(NotificationCreateData createData, RequestContext context) {
        NotificationId id = this.idGenerator.generateId(potential -> getById(potential, context).isPresent());
        Notification notification = Notification.from(id, createData, context);

        this.eventPublisher.firePreCreate(notification, createData, context);
        this.dao.create(notification, context);
        this.eventPublisher.firePostCreate(notification, createData, context);

        return notification;
    }

    @Override
    public Notification updateWithData(NotificationId id, NotificationUpdateData updateData, RequestContext context) {
        Notification current = getById(id, context).orElseThrow(notFound(id));
        Notification updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return updated;
    }

    @Override
    public void deleteById(NotificationId id, RequestContext context) {
        Notification notification = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(notification, context);
        this.dao.delete(notification, context);
        this.eventPublisher.firePostDelete(notification, context);
    }
}
