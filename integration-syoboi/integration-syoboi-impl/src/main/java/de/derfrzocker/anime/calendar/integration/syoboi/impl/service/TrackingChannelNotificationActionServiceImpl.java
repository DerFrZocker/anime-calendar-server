package de.derfrzocker.anime.calendar.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.dao.TrackingChannelNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.syoboi.service.TrackingChannelNotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.exception.TrackingChannelNotificationActionExceptions.inconsistentNotFound;

@ApplicationScoped
public class TrackingChannelNotificationActionServiceImpl implements TrackingChannelNotificationActionService {

    @Inject
    TrackingChannelNotificationActionDao dao;

    @Override
    public Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public TrackingChannelNotificationAction createWithData(NotificationActionId id,
                                                            TrackingChannelNotificationActionCreateData createData,
                                                            RequestContext context) {
        TrackingChannelNotificationAction action = TrackingChannelNotificationAction.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
