package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import de.derfrzocker.anime.calendar.server.model.domain.anime.Anime;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

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
    public MessageBuilder render(NotificationHolder holder, RequestContext context) {
        List<IntegrationLinkNotificationAction> integrationLinks = toSpecificAction(holder.actions(), context);
        EmbedBuilder embed = new EmbedBuilder();

        if (integrationLinks.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            return new MessageBuilder().addEmbed(new EmbedBuilder().setTitle("ERROR: Nothing found"));
        }

        // TODO 2025-02-23: Better error handling
        Anime anime = this.animeService.getById(integrationLinks.getFirst().animeId(), context).orElseThrow();
        embed.setTitle("[%s] %s".formatted(anime.id().raw(), anime.title())).setDescription("Found following links:");

        // TODO 2024-12-23: Account for message limits
        List<LowLevelComponent> buttons = new ArrayList<>();
        for (IntegrationLinkNotificationAction action : integrationLinks) {
            IntegrationId integrationId = action.integrationId();
            embed.addField("[%s] [%s] [%s] %s".formatted(integrationId.raw(),
                                                         action.integrationAnimeId().raw(),
                                                         action.score(),
                                                         action.bestName()),
                           getUrl(integrationId, action.integrationAnimeId()));
            if (buttons.size() >= 5) {
                continue;
            }

            buttons.add(Button.create(action.id().raw(),
                                      ButtonStyle.PRIMARY,
                                      "Link [%s] %s".formatted(integrationId.raw(),
                                                               action.integrationAnimeId().raw())));
        }

        return new MessageBuilder().addEmbed(embed).addComponents(ActionRow.of(buttons));
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
