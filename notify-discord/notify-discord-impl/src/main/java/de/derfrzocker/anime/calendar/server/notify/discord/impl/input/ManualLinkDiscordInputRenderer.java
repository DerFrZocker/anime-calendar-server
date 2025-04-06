package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import static de.derfrzocker.anime.calendar.server.integration.exception.ManualLinkNotificationActionExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.service.ManualLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named("ManualLink" + DiscordInputRenderer.NAME_SUFFIX)
public class ManualLinkDiscordInputRenderer implements DiscordInputRenderer {

    @Inject
    ManualLinkNotificationActionService actionService;

    @Override
    public void render(NotificationAction action, DiscordInputBuilder builder, RequestContext context) {
        ManualLinkNotificationAction linkAction = this.actionService.getById(action.id(), context)
                                                                    .orElseThrow(inconsistentNotFound(action.id()));

        builder.setTitle("Manual Link %s to %s".formatted(linkAction.animeId().raw(),
                                                          linkAction.integrationId().raw()));

        builder.addTextField("integrationAnimeId", "Link Integration Anime Id", 1, 20);
    }
}
