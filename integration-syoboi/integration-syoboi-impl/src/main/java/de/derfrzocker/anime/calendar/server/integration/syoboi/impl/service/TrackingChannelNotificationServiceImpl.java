package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.TrackingChannelNotificationDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationExceptions;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class TrackingChannelNotificationServiceImpl implements TrackingChannelNotificationService {

    @Inject
    TrackingChannelNotificationDao dao;

    @Override
    public Optional<TrackingChannelNotification> getById(NotificationId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public TrackingChannelNotification createWithData(
            NotificationId id,
            TrackingChannelNotificationCreateData createData,
            RequestContext context) {

        Optional<TrackingChannelNotification> optional = getById(id, context);
        if (optional.isPresent()) {
            throw TrackingChannelNotificationExceptions.alreadyCreated(id).get();
        }

        TrackingChannelNotification action = TrackingChannelNotification.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(TrackingChannelNotificationExceptions.inconsistentNotFound(id));
    }
}
