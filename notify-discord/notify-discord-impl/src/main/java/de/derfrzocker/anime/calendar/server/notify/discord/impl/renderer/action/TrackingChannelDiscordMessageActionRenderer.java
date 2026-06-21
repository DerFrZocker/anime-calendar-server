package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer.action;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.exception.TrackingChannelNotificationActionExceptions;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageActionRenderer;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Identifier(TrackingChannelNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class TrackingChannelDiscordMessageActionRenderer implements DiscordMessageActionRenderer {

    @Inject
    TrackingChannelNotificationActionService trackingActionService;

    @Override
    public void render(
            NotificationHolder holder,
            NotificationAction action,
            DiscordMessageBuilder builder,
            RequestContext context) {

        TrackingChannelNotificationAction trackingChannel = toSpecificAction(action, context);

        builder.addField("Channel name:", trackingChannel.channelName());
        builder.addButton("Link %s".formatted(trackingChannel.channelName()), action.id().raw());
    }

    private TrackingChannelNotificationAction toSpecificAction(NotificationAction action, RequestContext context) {
        return this.trackingActionService
                .getById(action.id(), context)
                .orElseThrow(TrackingChannelNotificationActionExceptions.inconsistentNotFound(action.id()));
    }
}
