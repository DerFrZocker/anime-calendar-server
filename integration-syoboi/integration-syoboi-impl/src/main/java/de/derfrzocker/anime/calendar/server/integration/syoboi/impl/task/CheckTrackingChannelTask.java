package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ResolvedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.exception.ChannelExceptions;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.CheckTrackingChannelTaskConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.ResolvedChannelService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationHelperService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Instant;
import java.time.YearMonth;

@ApplicationScoped
public class CheckTrackingChannelTask {

    @Inject
    TrackingChannelNotificationActionService trackingActionService;
    @Inject
    TrackingChannelNotificationService trackingService;
    @Inject
    TIDDataService tidDataService;
    @Inject
    NotificationActionService actionService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationHelperService helperService;
    @Inject
    ResolvedChannelService channelService;
    @Inject
    CheckTrackingChannelTaskConfig config;

    public void onTIDDataCreate(@Observes PostTIDDataCreateEvent createEvent) {
        if (createEvent.tidData().trackingChannelId() != null) {
            return;
        }

        checkTracking(createEvent.tidData(), createEvent.context());
    }

    public void onTIDDataUpdate(@Observes PostTIDDataUpdateEvent updateEvent) {
        if (updateEvent.current().firstChannelIds().equals(updateEvent.updated().firstChannelIds())) {
            return;
        }

        checkTracking(updateEvent.updated(), updateEvent.context());
    }

    private void checkTracking(TIDData tidData, RequestContext context) {
        if (tidData.firstChannelIds().isEmpty()) {
            return;
        }

        if (tidData.firstChannelIds().size() == 1) {
            this.tidDataService.updateWithData(
                    tidData.tid(), new TIDDataUpdateData(
                            Change.nothing(),
                            Change.to(tidData.firstChannelIds().getFirst()),
                            Change.nothing(),
                            Change.nothing(),
                            Change.nothing(),
                            Change.nothing(),
                            Change.nothing()), context);
            return;
        }

        if (tidData.firstEnd() != null) {
            if (tidData.firstEnd().isBefore(YearMonth.now().minusMonths(3))) {
                return;
            }
        }

        sendNotification(tidData, context);
    }

    private void sendNotification(TIDData tidData, RequestContext context) {
        NotificationId notificationId = createTrackingChannelNotification(tidData, context);

        int priority = 0;
        for (ChannelId channelId : tidData.firstChannelIds()) {
            createTrackingChannelNotificationAction(notificationId, priority++, tidData, channelId, context);
        }

        this.helperService.send(notificationId, context);
    }

    private String getChannelName(ChannelId channelId, RequestContext context) {
        return this.channelService
                .resolveById(channelId, context)
                .map(ResolvedChannel::name)
                .orElseThrow(ChannelExceptions.inconsistentNotFound(channelId));
    }

    private NotificationId createTrackingChannelNotification(TIDData tidData, RequestContext context) {
        Instant validUntil = Instant.now().plus(this.config.validDuration());
        var createData = new NotificationCreateData(TrackingChannelNotification.NOTIFICATION_TYPE, validUntil);
        NotificationId id = this.notificationService.createWithData(createData, context).id();

        ChannelId channelId = tidData.trackingChannelId();
        String channelName = null;
        if (channelId != null) {
            channelName = getChannelName(channelId, context);
        }
        var specificCreateData = new TrackingChannelNotificationCreateData(
                tidData.tid(),
                tidData.title(),
                channelId,
                channelName);
        this.trackingService.createWithData(id, specificCreateData, context);

        return id;
    }

    private void createTrackingChannelNotificationAction(
            NotificationId id,
            int priority,
            TIDData tidData,
            ChannelId channelId,
            RequestContext context) {

        var createData = new NotificationActionCreateData(
                id,
                TrackingChannelNotificationAction.NOTIFICATION_ACTION_TYPE,
                priority,
                false);
        NotificationActionId actionId = this.actionService.createWithData(createData, context).id();

        String channelName = getChannelName(channelId, context);
        var specificCreateData = new TrackingChannelNotificationActionCreateData(tidData.tid(), channelId, channelName);

        this.trackingActionService.createWithData(actionId, specificCreateData, context);
    }
}
