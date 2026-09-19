package de.derfrzocker.anime.calendar.notify.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.notify.api.Notification;
import de.derfrzocker.anime.calendar.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.notify.dao.NotificationDao;
import de.derfrzocker.anime.calendar.notify.impl.generator.NotificationIdGenerator;
import de.derfrzocker.anime.calendar.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class NotificationServiceImpl implements NotificationService {

    @Inject
    NotificationDao dao;
    @Inject
    NotificationIdGenerator idGenerator;

    @Override
    public Optional<Notification> getById(NotificationId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Notification createWithData(NotificationCreateData createData, RequestContext context) {
        NotificationId id = this.idGenerator.generateId(potential -> getById(potential, context).isPresent());
        Notification notification = Notification.from(id, createData, context);

        this.dao.create(notification, context);

        return notification;
    }
}
