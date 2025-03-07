package de.derfrzocker.anime.calendar.server.integration.impl.task;

import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import static de.derfrzocker.anime.calendar.server.integration.exception.IntegrationLinkNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class LinkAnimeActionTask {

    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("IntegrationLink");

    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    IntegrationLinkNotificationActionService actionService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!NOTIFICATION_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        IntegrationLinkNotificationAction action = this.actionService.getById(event.action().id(), event.context())
                                                                     .orElseThrow(inconsistentNotFound(event.action()
                                                                                                            .id()));

        this.linkService.createWithData(action.animeId(),
                                        action.integrationId(),
                                        action.integrationAnimeId(),
                                        new AnimeIntegrationLinkCreateData(),
                                        event.context());
    }
}
