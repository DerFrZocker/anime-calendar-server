package de.derfrzocker.anime.calendar.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.mapper.TrackingChannelNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.mapper.TrackingChannelNotificationActionDataMapper.toData;

@Dependent
public class TrackingChannelNotificationActionMongoDBDaoImpl implements TrackingChannelNotificationActionDao {

    @Inject
    TrackingChannelNotificationActionMongoDBRepository repository;

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
}
