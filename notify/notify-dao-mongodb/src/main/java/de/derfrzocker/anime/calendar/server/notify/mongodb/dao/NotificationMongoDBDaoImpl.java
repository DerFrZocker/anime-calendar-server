package de.derfrzocker.anime.calendar.server.notify.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.notify.mongodb.mapper.NotificationDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.dao.NotificationDao;
import de.derfrzocker.anime.calendar.server.notify.mongodb.mapper.NotificationDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class NotificationMongoDBDaoImpl implements NotificationDao {

    @Inject
    NotificationMongoDBRepository repository;

    @Override
    public Stream<Notification> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(NotificationDataMapper::toDomain);
    }

    @Override
    public Optional<Notification> getById(NotificationId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(NotificationDataMapper::toDomain);
    }

    @Override
    public void create(Notification notification, RequestContext context) {
        this.repository.persist(toData(notification));
    }

    @Override
    public void update(Notification notification, RequestContext context) {
        this.repository.update(toData(notification));
    }

    @Override
    public void delete(Notification notification, RequestContext context) {
        this.repository.deleteById(notification.id());
    }
}
