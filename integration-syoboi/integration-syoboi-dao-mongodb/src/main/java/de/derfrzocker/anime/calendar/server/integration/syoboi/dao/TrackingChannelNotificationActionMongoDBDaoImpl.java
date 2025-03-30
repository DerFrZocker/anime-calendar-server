package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.TrackingChannelNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.TrackingChannelNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class TrackingChannelNotificationActionMongoDBDaoImpl implements TrackingChannelNotificationActionDao {

    @Inject
    TrackingChannelNotificationActionMongoDBRepository repository;

    @Override
    public Stream<TrackingChannelNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(TrackingChannelNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(TrackingChannelNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(TrackingChannelNotificationAction action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(TrackingChannelNotificationAction action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(TrackingChannelNotificationAction action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
