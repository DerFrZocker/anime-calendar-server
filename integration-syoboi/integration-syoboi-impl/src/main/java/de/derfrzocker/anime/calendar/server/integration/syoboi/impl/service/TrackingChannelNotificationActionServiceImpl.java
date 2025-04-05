package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.TrackingChannelNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class TrackingChannelNotificationActionServiceImpl implements TrackingChannelNotificationActionService {

    @Inject
    TrackingChannelNotificationActionDao dao;
    @Inject
    TrackingChannelNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<TrackingChannelNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<TrackingChannelNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public TrackingChannelNotificationAction createWithData(NotificationActionId id,
                                                            TrackingChannelNotificationActionCreateData createData,
                                                            RequestContext context) {
        TrackingChannelNotificationAction action = TrackingChannelNotificationAction.from(id, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public TrackingChannelNotificationAction updateWithData(NotificationActionId id,
                                                            TrackingChannelNotificationActionUpdateData updateData,
                                                            RequestContext context) {
        TrackingChannelNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        TrackingChannelNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        TrackingChannelNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
