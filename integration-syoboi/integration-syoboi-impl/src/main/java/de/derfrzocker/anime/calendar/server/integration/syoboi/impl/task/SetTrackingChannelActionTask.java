package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class SetTrackingChannelActionTask {

    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("TrackingChannel");

    @Inject
    TIDDataService tidDataService;
    @Inject
    TrackingChannelNotificationActionService actionService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!NOTIFICATION_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        TrackingChannelNotificationAction action = this.actionService.getById(event.action().id(), event.context())
                                                                     .orElseThrow(inconsistentNotFound(event.action()
                                                                                                            .id()));

        this.tidDataService.updateWithData(action.tid(),
                                           new TIDDataUpdateData(Change.nothing(),
                                                                 Change.to(action.channelId()),
                                                                 Change.nothing(),
                                                                 Change.nothing(),
                                                                 Change.nothing(),
                                                                 Change.nothing(),
                                                                 Change.nothing()),
                                           event.context());
    }
}
