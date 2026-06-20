package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.task;

import static de.derfrzocker.anime.calendar.server.anime.exception.NewAnimeNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionType;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeCreateData;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLinkCreateData;
import de.derfrzocker.anime.calendar.server.integration.service.AnimeIntegrationLinkService;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CreateNewAnimeActionTask {

    public static final NotificationActionType NOTIFICATION_ACTION_TYPE = new NotificationActionType("NewAnime");

    @Inject
    AnimeService animeService;
    @Inject
    AnimeIntegrationLinkService linkService;
    @Inject
    NewAnimeNotificationActionService actionService;

    public void onActionTrigger(@Observes NotificationActionTriggerEvent event) {
        if (!NOTIFICATION_ACTION_TYPE.equals(event.action().actionType())) {
            return;
        }

        NewAnimeNotificationAction action = this.actionService
                .getById(event.action().id(), event.context())
                .orElseThrow(inconsistentNotFound(event.action().id()));

        this.animeService.createWithData(
                new AnimeCreateData(action.title(), action.episodeCount(), List.of()),
                event.context(),
                anime -> createLinks(anime, action, event.context()));
    }

    private void createLinks(Anime anime, NewAnimeNotificationAction action, RequestContext context) {
        this.linkService.createWithData(
                anime.id(),
                action.sourceIntegrationId(),
                action.sourceIntegrationAnimeId(),
                new AnimeIntegrationLinkCreateData(),
                context);

        if (action.integrationId() != null && action.integrationAnimeId() != null) {
            this.linkService.createWithData(
                    anime.id(),
                    action.integrationId(),
                    action.integrationAnimeId(),
                    new AnimeIntegrationLinkCreateData(),
                    context);
        }
    }
}
