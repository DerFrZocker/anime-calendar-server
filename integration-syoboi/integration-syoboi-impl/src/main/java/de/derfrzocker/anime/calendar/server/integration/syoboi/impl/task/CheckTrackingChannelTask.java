package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataCreateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.event.PostTIDDataUpdateEvent;
import de.derfrzocker.anime.calendar.server.integration.syoboi.impl.config.SyoboiConfig;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationCreateData;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
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

    public static final NotificationType NOTIFICATION_TYPE = new NotificationType("TrackingChannel");
    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("TrackingChannel");

    @Inject
    TrackingChannelNotificationActionService trackingActionService;
    @Inject
    TIDDataService tidDataService;
    @Inject
    NotificationActionService actionService;
    @Inject
    NotificationService notificationService;
    @Inject
    NotificationHelperService helperService;
    @Inject
    SyoboiConfig config;

    public void onTIDDataCreate(@Observes PostTIDDataCreateEvent createEvent) {
        if (createEvent.tidData().trackingChannel() != null) {
            return;
        }

        checkTracking(createEvent.tidData(), createEvent.context());
    }

    public void onTIDDataUpdate(@Observes PostTIDDataUpdateEvent updateEvent) {
        if (updateEvent.current().firstChannels().equals(updateEvent.updated().firstChannels())) {
            return;
        }

        checkTracking(updateEvent.updated(), updateEvent.context());
    }

    private void checkTracking(TIDData tidData, RequestContext context) {
        if (tidData.firstChannels().isEmpty()) {
            return;
        }

        if (tidData.firstChannels().size() == 1) {
            this.tidDataService.updateWithData(tidData.tid(),
                                               new TIDDataUpdateData(Change.nothing(),
                                                                     Change.to(tidData.firstChannels().getFirst()),
                                                                     Change.nothing(),
                                                                     Change.nothing(),
                                                                     Change.nothing(),
                                                                     Change.nothing(),
                                                                     Change.nothing()),
                                               context);
            return;
        }

        if (tidData.firstEnd() != null) {
            if (tidData.firstEnd().isBefore(YearMonth.now().plusMonths(3))) {
                return;
            }
        }

        sendNotification(tidData, context);
    }

    private void sendNotification(TIDData tidData, RequestContext context) {
        Notification notification = createNewNotification(context);

        for (Channel channel : tidData.firstChannels()) {
            NotificationAction notificationAction = createNewNotificationAction(notification.id(), context);
            createNewNotificationAction(notificationAction.id(), tidData, channel, context);
        }

        this.helperService.send(notification.id(), context);
    }

    private Notification createNewNotification(RequestContext context) {
        Instant validUntil = Instant.now().plus(this.config.getTrackingChannelActionValidLength());
        NotificationCreateData createData = new NotificationCreateData(NOTIFICATION_TYPE, validUntil);

        return this.notificationService.createWithData(createData, context);
    }

    private NotificationAction createNewNotificationAction(NotificationId id, RequestContext context) {
        NotificationActionCreateData createData = new NotificationActionCreateData(id, NOTIFICATION_ACTION_TYPE);
        return this.actionService.createWithData(createData, context);
    }

    private void createNewNotificationAction(NotificationActionId actionId,
                                             TIDData tidData,
                                             Channel channel,
                                             RequestContext context) {
        TrackingChannelNotificationActionCreateData createData = createData(tidData, channel);
        this.trackingActionService.createWithData(actionId, createData, context);
    }

    private TrackingChannelNotificationActionCreateData createData(TIDData tidData, Channel channel) {
        return new TrackingChannelNotificationActionCreateData(tidData.tid(), tidData.title(), channel);
    }
}
