package de.derfrzocker.anime.calendar.server.integration.impl.task;

import static de.derfrzocker.anime.calendar.server.integration.exception.ManualLinkNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.service.ManualLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ManualLinkActionTask {

    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("ManualLink");

    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    ManualLinkNotificationActionService actionService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!NOTIFICATION_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        ManualLinkNotificationAction action = this.actionService.getById(event.action().id(), event.context())
                                                                .orElseThrow(inconsistentNotFound(event.action()
                                                                                                       .id()));

        this.linkService.createWithData(action.animeId(),
                                        action.integrationId(),
                                        action.integrationAnimeId(),
                                        new AnimeIntegrationLinkCreateData(),
                                        event.context());
    }
}
