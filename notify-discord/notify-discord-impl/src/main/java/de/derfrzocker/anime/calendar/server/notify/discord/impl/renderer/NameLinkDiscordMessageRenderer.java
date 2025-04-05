package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("NameLink" + DiscordMessageRenderer.NAME_SUFFIX)
public class NameLinkDiscordMessageRenderer implements DiscordMessageRenderer {

    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    @Inject
    AnimeService animeService;
    @Inject
    IntegrationLinkNotificationActionService integrationActionService;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        List<IntegrationLinkNotificationAction> integrationLinks = toSpecificAction(holder.actions(), context);

        if (integrationLinks.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            builder.setTitle("ERROR: Nothing found");
            return;
        }

        // TODO 2025-02-23: Better error handling
        Anime anime = this.animeService.getById(integrationLinks.getFirst().animeId(), context).orElseThrow();
        builder.setTitle("[%s] %s".formatted(anime.id().raw(), anime.title())).setDescription("Found following links:");

        // TODO 2024-12-23: Account for message limits
        for (IntegrationLinkNotificationAction action : integrationLinks) {
            IntegrationId integrationId = action.integrationId();
            builder.addField("[%s] [%s] [%s] %s".formatted(integrationId.raw(),
                                                           action.integrationAnimeId().raw(),
                                                           action.score(),
                                                           action.bestName()),
                             getUrl(integrationId, action.integrationAnimeId()));

            builder.addButton("Link [%s] %s".formatted(integrationId.raw(), action.integrationAnimeId().raw()),
                              action.id().raw());
        }
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }

    private List<IntegrationLinkNotificationAction> toSpecificAction(List<NotificationAction> actions,
                                                                     RequestContext context) {
        return actions.stream()
                      .map(NotificationAction::id)
                      .map(id -> this.integrationActionService.getById(id, context))
                      .filter(optional -> {
                          if (optional.isEmpty()) {
                              // TODO 2025-02-23: Log warning
                              return false;
                          }
                          return true;
                      })
                      .map(Optional::get)
                      .sorted(Comparator.comparingInt(IntegrationLinkNotificationAction::score))
                      .toList();
    }
}
