package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@ApplicationScoped
@Named("NewAnime" + DiscordMessageRenderer.NAME_SUFFIX)
public class NewAnimeDiscordMessageRenderer implements DiscordMessageRenderer {

    private static final IntegrationId SYOBOI = new IntegrationId("syoboi");
    private static final IntegrationId MY_ANIME_LIST = new IntegrationId("myanimelist");
    private static final IntegrationId ANIDB = new IntegrationId("anidb");

    @Inject
    NewAnimeNotificationActionService newAnimeActionService;

    @Override
    public MessageCreateBuilder render(NotificationHolder holder, RequestContext context) {
        List<NewAnimeNotificationAction> newAnimes = toSpecificAction(holder.actions(), context);
        EmbedBuilder embed = new EmbedBuilder();

        if (newAnimes.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            return new MessageCreateBuilder().addEmbeds(new EmbedBuilder().setTitle("ERROR: Nothing found").build());
        }

        Map<IntegrationId, Set<IntegrationAnimeId>> integrations = new HashMap<>();

        newAnimes.forEach(newAnime -> {
            newAnime.links().forEach((integrationId, integrationAnimeId) -> {
                integrations.computeIfAbsent(integrationId, k -> new HashSet<>()).add(integrationAnimeId);
            });
        });

        int minAmount = Integer.MAX_VALUE;
        IntegrationId min = null;

        for (Map.Entry<IntegrationId, Set<IntegrationAnimeId>> entry : integrations.entrySet()) {
            if (entry.getValue().size() < minAmount) {
                min = entry.getKey();
                minAmount = entry.getValue().size();
            }
        }

        List<ItemComponent> buttons = new ArrayList<>();

        embed.setTitle("New Anime found:");

        if (min != null) {
            for (IntegrationAnimeId animeId : integrations.get(min)) {
                embed.addField("[%s] [%s]".formatted(min.raw(), animeId.raw()), getUrl(min, animeId), false);
                for (NewAnimeNotificationAction action : newAnimes) {
                    if (!Objects.equals(action.links().get(min), animeId)) {
                        continue;
                    }

                    for (Map.Entry<IntegrationId, IntegrationAnimeId> entry : action.links().entrySet()) {
                        if (Objects.equals(entry.getKey(), min)) {
                            continue;
                        }
                        embed.addField("-> [%s] [%s] [%s] %s".formatted(entry.getKey().raw(),
                                                                        entry.getValue().raw(),
                                                                        action.score(),
                                                                        action.title()),
                                       getUrl(entry.getKey(), entry.getValue()),
                                       false);

                        if (buttons.size() >= 5) {
                            continue;
                        }

                        buttons.add(Button.of(ButtonStyle.PRIMARY,
                                              action.id().raw(),
                                              "Create [%s] %s".formatted(entry.getKey().raw(),
                                                                         entry.getValue().raw())));
                    }
                }
            }
        }

        return new MessageCreateBuilder().addEmbeds(embed.build()).addComponents(ActionRow.of(buttons));
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s/time".formatted(integrationAnimeId.raw());
        }
        if (MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }

    private List<NewAnimeNotificationAction> toSpecificAction(List<NotificationAction> actions,
                                                              RequestContext context) {
        return actions.stream()
                      .map(NotificationAction::id)
                      .map(id -> this.newAnimeActionService.getById(id, context))
                      .filter(optional -> {
                          if (optional.isEmpty()) {
                              // TODO 2025-02-23: Log warning
                              return false;
                          }
                          return true;
                      })
                      .map(Optional::get)
                      .sorted(Comparator.comparingInt(NewAnimeNotificationAction::score))
                      .toList();
    }
}
