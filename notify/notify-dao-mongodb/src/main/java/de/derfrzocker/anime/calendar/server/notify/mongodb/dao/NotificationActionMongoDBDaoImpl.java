package de.derfrzocker.anime.calendar.server.notify.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.notify.mongodb.mapper.NotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.dao.NotificationActionDao;
import de.derfrzocker.anime.calendar.server.notify.mongodb.mapper.NotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class NotificationActionMongoDBDaoImpl implements NotificationActionDao {

    @Inject
    NotificationActionMongoDBRepository repository;

    @Override
    public Stream<NotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(NotificationActionDataMapper::toDomain);
    }

    @Override
    public Stream<NotificationAction> getAllWithData(NotificationId notificationId, RequestContext context) {
        return this.repository.find("notificationId", notificationId.raw())
                              .stream()
                              .map(NotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<NotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(NotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(NotificationAction notificationAction, RequestContext context) {
        this.repository.persist(toData(notificationAction));
    }

    @Override
    public void update(NotificationAction notificationAction, RequestContext context) {
        this.repository.update(toData(notificationAction));
    }

    @Override
    public void delete(NotificationAction notificationAction, RequestContext context) {
        this.repository.deleteById(notificationAction.id());
    }
}
