package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationExceptions;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationService;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@Named(StreamingNotification.NOTIFICATION_TYPE_RAW + DiscordMessageRenderer.NAME_SUFFIX)
public class StreamingDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    StreamingNotificationService streamingService;
    @Inject
    StreamingNotificationActionService streamingActionService;
    @Inject
    IntegrationHelperService integrationHelperService;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        StreamingNotification notification = toNotification(holder.notification(), context);

        builder.setTitle("[%s] Missing Stream".formatted(notification.animeId().raw()));

        String url = this.integrationHelperService.getUrl(notification.referenceIntegrationId(),
                                                          notification.referenceIntegrationAnimeId());
        builder.addField("%s [%s] - ep: %d".formatted(notification.referenceIntegrationId().raw(),
                                                      notification.referenceIntegrationAnimeId().raw(),
                                                      notification.orgEpisodeIndex() + 1),
                         "%s\n%s".formatted(notification.orgStreamingTime(), url));

        List<StreamingNotificationAction> streamingActions = toSpecificAction(holder.actions(), context);

        for (StreamingNotificationAction action : streamingActions) {
            builder.addButton("Set Streaming", action.id().raw());
        }
    }

    private StreamingNotification toNotification(Notification notify, RequestContext context) {
        return this.streamingService.getById(notify.id(), context)
                                    .orElseThrow(StreamingNotificationExceptions.inconsistentNotFound(notify.id()));
    }

    private List<StreamingNotificationAction> toSpecificAction(List<NotificationAction> actions,
                                                               RequestContext context) {
        return actions.stream()
                      .filter(action -> Objects.equals(action.actionType(),
                                                       StreamingNotificationAction.NOTIFICATION_ACTION_TYPE))
                      .map(NotificationAction::id)
                      .map(id -> this.streamingActionService.getById(id, context))
                      .filter(optional -> {
                          if (optional.isEmpty()) {
                              // TODO 2025-02-23: Log warning
                              return false;
                          }
                          return true;
                      })
                      .map(Optional::get)
                      .toList();
    }
}
