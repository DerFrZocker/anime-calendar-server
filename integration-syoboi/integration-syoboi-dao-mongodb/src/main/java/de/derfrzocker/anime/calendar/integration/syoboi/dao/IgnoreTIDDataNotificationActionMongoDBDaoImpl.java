package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.mapper.IgnoreTIDDataNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.mapper.IgnoreTIDDataNotificationActionDataMapper.toData;

@Dependent
public class IgnoreTIDDataNotificationActionMongoDBDaoImpl implements IgnoreTIDDataNotificationActionDao {

    @Inject
    IgnoreTIDDataNotificationActionMongoDBRepository repository;

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
}
