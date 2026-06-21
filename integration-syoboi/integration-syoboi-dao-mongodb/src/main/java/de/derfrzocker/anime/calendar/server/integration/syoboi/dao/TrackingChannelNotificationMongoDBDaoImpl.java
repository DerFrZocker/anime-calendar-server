package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.mapper.TrackingChannelNotificationDataMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class TrackingChannelNotificationMongoDBDaoImpl implements TrackingChannelNotificationDao {

    @Inject
    TrackingChannelNotificationMongoDBRepository repository;

    @Override
    public Optional<TrackingChannelNotification> getById(NotificationId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(TrackingChannelNotificationDataMapper::toDomain);
    }

    @Override
    public void create(TrackingChannelNotification notification, RequestContext context) {
        this.repository.persist(TrackingChannelNotificationDataMapper.toData(notification));
    }
}
