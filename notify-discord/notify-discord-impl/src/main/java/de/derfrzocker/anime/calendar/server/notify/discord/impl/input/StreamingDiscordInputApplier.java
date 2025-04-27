package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputApplier;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputValuesProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.Instant;

@ApplicationScoped
@Named(StreamingNotificationAction.NOTIFICATION_ACTION_TYPE_RAW + DiscordInputApplier.NAME_SUFFIX)
public class StreamingDiscordInputApplier implements DiscordInputApplier {

    @Inject
    StreamingNotificationActionService actionService;

    @Override
    public void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context) {
        IntegrationId integrationId = new IntegrationId(provider.getInput("integrationId"));
        IntegrationAnimeId integrationAnimeId = new IntegrationAnimeId(provider.getInput("integrationAnimeId"));
        int streamingEpisode = Integer.parseInt(provider.getInput("streamingEpisode"));
        Instant streamingTime = Instant.parse(provider.getInput("streamingTime"));
        this.actionService.updateWithData(action.id(),
                                          new StreamingNotificationActionUpdateData(Change.to(integrationId),
                                                                                    Change.to(integrationAnimeId),
                                                                                    Change.to(streamingEpisode),
                                                                                    Change.to(streamingTime)),
                                          context);
    }
}
