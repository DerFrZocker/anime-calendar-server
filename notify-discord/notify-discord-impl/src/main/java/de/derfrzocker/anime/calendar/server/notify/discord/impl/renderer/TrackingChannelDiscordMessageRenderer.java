package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("TrackingChannel" + DiscordMessageRenderer.NAME_SUFFIX)
public class TrackingChannelDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    TrackingChannelNotificationActionService trackingActionService;

    @Override
    public void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context) {
        List<TrackingChannelNotificationAction> trackingChannels = toSpecificAction(holder.actions(), context);

        if (trackingChannels.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            builder.setTitle("ERROR [TrackingChannel]: Nothing found");
            return;
        }

        TID tid = trackingChannels.getFirst().tid();
        String title = trackingChannels.getFirst().title();
        builder.setTitle("[%s] %s".formatted(tid.raw(), title))
               .setDescription("Links: %s\nFound following channels:".formatted(getUrl(tid)));

        // TODO 2024-12-23: Account for message limits
        for (TrackingChannelNotificationAction action : trackingChannels) {
            builder.addField("Channel name:", action.channelName());

            builder.addButton("Link %s".formatted(action.channelName()), action.id().raw());
        }
    }

    private String getUrl(TID tid) {
        return "https://cal.syoboi.jp/tid/%s".formatted(tid.raw());
    }

    private List<TrackingChannelNotificationAction> toSpecificAction(List<NotificationAction> actions,
                                                                     RequestContext context) {
        return actions.stream()
                      .map(NotificationAction::id)
                      .map(id -> this.trackingActionService.getById(id, context))
                      .filter(optional -> {
                          if (optional.isEmpty()) {
                              // TODO 2025-03-10: Log warning
                              return false;
                          }
                          return true;
                      })
                      .map(Optional::get)
                      .toList();
    }
}
