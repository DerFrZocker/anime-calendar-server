package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named(StreamingNotificationAction.NOTIFICATION_ACTION_TYPE_RAW + DiscordInputRenderer.NAME_SUFFIX)
public class StreamingDiscordInputRenderer implements DiscordInputRenderer {

    @Inject
    StreamingNotificationActionService actionService;

    @Override
    public void render(NotificationAction action, DiscordInputBuilder builder, RequestContext context) {
        StreamingNotificationAction linkAction = this.actionService.getById(action.id(), context)
                                                                   .orElseThrow(inconsistentNotFound(action.id()));

        builder.setTitle("Set streaming time for %s".formatted(linkAction.animeId().raw()));

        builder.addTextField("integrationId", "Streaming service", 1, 20);
        builder.addTextField("integrationAnimeId", "Streaming anime id", 1, 20);
        builder.addTextField("streamingEpisode", "Episode number", 1, 5);
        builder.addTextField("streamingTime", "Streaming time", 23, 25);
    }
}
