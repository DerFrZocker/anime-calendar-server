package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.IgnoreTIDDataNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.IgnoreTIDDataNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class IgnoreTIDDataNotificationActionMongoDBDaoImpl implements IgnoreTIDDataNotificationActionDao {

    @Inject
    IgnoreTIDDataNotificationActionMongoDBRepository repository;

    @Override
    public Stream<IgnoreTIDDataNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(IgnoreTIDDataNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(IgnoreTIDDataNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(IgnoreTIDDataNotificationAction action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(IgnoreTIDDataNotificationAction action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(IgnoreTIDDataNotificationAction action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
