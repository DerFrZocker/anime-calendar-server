package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationIds;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationHelperService;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotification;
import de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationExceptions;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationService;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageActionRenderer;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(TrackingChannelNotification.NOTIFICATION_TYPE_RAW)
public class TrackingChannelDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    TrackingChannelNotificationService trackingChannelNotificationService;
    @Inject
    IntegrationHelperService integrationHelperService;
    @Any
    @Inject
    Instance<DiscordMessageActionRenderer> actionRenderers;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        TrackingChannelNotification notification = toNotification(holder.notification(), context);

        TID tid = notification.tid();
        String title = notification.title();
        String url = this.integrationHelperService.getUrl(IntegrationIds.SYOBOI, new IntegrationAnimeId(tid.raw()));

        builder.setTitle("[%s] %s".formatted(tid.raw(), title));

        if (notification.currentChannelId() == null) {
            builder.setDescription("Link: %s\nFound following channels:".formatted(url));
        } else {
            builder.setDescription("Link: %s\nCurrent Channel: %s\nFound following channels:".formatted(
                    url,
                    notification.currentChannelName()));
        }

        for (NotificationAction action : holder.actions()) {
            actionRenderers
                    .select(Identifier.Literal.of(action.actionType().raw()))
                    .get()
                    .render(holder, action, builder, context);
        }
    }

    private TrackingChannelNotification toNotification(Notification notify, RequestContext context) {
        return this.trackingChannelNotificationService
                .getById(notify.id(), context)
                .orElseThrow(TrackingChannelNotificationExceptions.inconsistentNotFound(notify.id()));
    }
}
