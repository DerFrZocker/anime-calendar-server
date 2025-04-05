package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.IgnoreTIDDataNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TIDDataUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TIDDataService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class IgnoreTIDDataActionTask {

    private static final NotificationActionType IGNORE_ACTION_TYPE = new NotificationActionType("IgnoreTIDData");

    @Inject
    TIDDataService tidDataService;
    @Inject
    IgnoreTIDDataNotificationActionService actionService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!IGNORE_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        IgnoreTIDDataNotificationAction action = this.actionService.getById(event.action().id(), event.context())
                                                                   .orElseThrow(inconsistentNotFound(event.action()
                                                                                                          .id()));

        this.tidDataService.updateWithData(action.tid(), createUpdateData(), event.context());
    }

    private TIDDataUpdateData createUpdateData() {
        return new TIDDataUpdateData(Change.nothing(),
                                     Change.nothing(),
                                     Change.nothing(),
                                     Change.nothing(),
                                     Change.nothing(),
                                     Change.to(false),
                                     Change.nothing());
    }
}
