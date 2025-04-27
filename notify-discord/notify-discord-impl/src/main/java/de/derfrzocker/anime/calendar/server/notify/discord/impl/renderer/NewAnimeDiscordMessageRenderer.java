package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.service.NewAnimeNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionType;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@ApplicationScoped
@Named("NewAnime" + DiscordMessageRenderer.NAME_SUFFIX)
public class NewAnimeDiscordMessageRenderer implements DiscordMessageRenderer {

    private static final NotificationActionType NEW_ANIME_ACTION_TYPE = new NotificationActionType("NewAnime");
    private static final NotificationActionType IGNORE_ACTION_TYPE = new NotificationActionType("IgnoreTIDData");

    @Inject
    NewAnimeNotificationActionService newAnimeActionService;
    @Inject
    IgnoreTIDDataNotificationActionService ignoreTIDDataActionService;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        List<NewAnimeNotificationAction> newAnimes = toSpecificAction(holder.actions(), context);

        if (holder.actions().isEmpty()) {
            // TODO 2025-02-23: Log better error here
            builder.setTitle("ERROR: Nothing found");
            return;
        }

        builder.setTitle("New Anime found:");

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

        if (min != null) {
            for (IntegrationAnimeId animeId : integrations.get(min)) {
                builder.addField("[%s] [%s]".formatted(min.raw(), animeId.raw()), getUrl(min, animeId));
                for (NewAnimeNotificationAction action : newAnimes) {
                    if (!Objects.equals(action.links().get(min), animeId)) {
                        continue;
                    }

                    for (Map.Entry<IntegrationId, IntegrationAnimeId> entry : action.links().entrySet()) {
                        if (Objects.equals(entry.getKey(), min)) {
                            continue;
                        }
                        builder.addField("-> [%s] [%s] [%s] %s".formatted(entry.getKey().raw(),
                                                                          entry.getValue().raw(),
                                                                          action.score(),
                                                                          action.title()),
                                         getUrl(entry.getKey(), entry.getValue()));

                        builder.addButton("Create [%s] %s".formatted(entry.getKey().raw(), entry.getValue().raw()),
                                          action.id().raw());
                    }
                }
            }
        }

        findIgnoreActions(holder.actions(), context).forEach(action -> {
            builder.addField("Ignorable [%s] [%s]".formatted(IntegrationIds.SYOBOI.raw(), action.tid().raw()),
                             getUrl(IntegrationIds.SYOBOI, new IntegrationAnimeId(action.tid().raw())));
            builder.addButton("Ignore [%s] %s".formatted(IntegrationIds.SYOBOI.raw(), action.tid().raw()),
                              action.id().raw());
        });

        findManualAction(holder.actions(), context).forEach(action -> {
            // TODO 2025-04-07: Make it not hardcoded to Syoboi
            IntegrationAnimeId animeId = action.links().get(IntegrationIds.SYOBOI);
            builder.addField("Manual Link [%s] [%s]".formatted(IntegrationIds.SYOBOI.raw(), animeId.raw()),
                             getUrl(IntegrationIds.SYOBOI, animeId));
            builder.addButton("Manual Link [%s] %s".formatted(IntegrationIds.SYOBOI.raw(), animeId.raw()),
                              action.id().raw());
        });
    }

    private String getUrl(IntegrationId integrationId, IntegrationAnimeId integrationAnimeId) {
        if (IntegrationIds.SYOBOI.equals(integrationId)) {
            return "https://cal.syoboi.jp/tid/%s/time".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.MY_ANIME_LIST.equals(integrationId)) {
            return "https://myanimelist.net/anime/%s".formatted(integrationAnimeId.raw());
        }
        if (IntegrationIds.ANIDB.equals(integrationId)) {
            return "https://anidb.net/anime/%s".formatted(integrationAnimeId.raw());
        }

        // TODO 2024-12-23: Better exception
        throw new RuntimeException("Unknown integration id: " + integrationId);
    }

    private List<NewAnimeNotificationAction> toSpecificAction(List<NotificationAction> actions,
                                                              RequestContext context) {
        return actions.stream()
                      .filter(action -> !action.requireUserInput())
                      .filter(action -> Objects.equals(action.actionType(), NEW_ANIME_ACTION_TYPE))
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

    private Stream<IgnoreTIDDataNotificationAction> findIgnoreActions(List<NotificationAction> actions,
                                                                      RequestContext context) {
        return actions.stream()
                      .filter(action -> Objects.equals(action.actionType(), IGNORE_ACTION_TYPE))
                      .map(NotificationAction::id)
                      .map(id -> this.ignoreTIDDataActionService.getById(id, context))
                      .filter(optional -> {
                          if (optional.isEmpty()) {
                              // TODO 2025-04-05: Log warning
                              return false;
                          }
                          return true;
                      })
                      .map(Optional::get);
    }

    private Stream<NewAnimeNotificationAction> findManualAction(List<NotificationAction> actions,
                                                                RequestContext context) {
        return actions.stream()
                      .filter(NotificationAction::requireUserInput)
                      .filter(action -> Objects.equals(action.actionType(), NEW_ANIME_ACTION_TYPE))
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
                      .sorted(Comparator.comparingInt(NewAnimeNotificationAction::score));
    }
}
