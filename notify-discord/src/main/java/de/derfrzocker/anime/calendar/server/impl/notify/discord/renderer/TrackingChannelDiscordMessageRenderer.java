package de.derfrzocker.anime.calendar.server.impl.notify.discord.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.impl.notify.discord.api.DiscordMessageRenderer;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

@ApplicationScoped
@Named("TrackingChannel" + DiscordMessageRenderer.NAME_SUFFIX)
public class TrackingChannelDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    TrackingChannelNotificationActionService trackingActionService;

    @Override
    public MessageBuilder render(NotificationHolder holder, RequestContext context) {
        List<TrackingChannelNotificationAction> trackingChannels = toSpecificAction(holder.actions(), context);
        EmbedBuilder embed = new EmbedBuilder();

        if (trackingChannels.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            return new MessageBuilder().addEmbed(new EmbedBuilder().setTitle("ERROR [TrackingChannel]: Nothing found"));
        }

        TID tid = trackingChannels.getFirst().tid();
        String title = trackingChannels.getFirst().title();
        embed.setTitle("[%s] %s".formatted(tid.raw(), title))
             .setDescription("Links: %s\nFound following channels:".formatted(getUrl(tid)));

        // TODO 2024-12-23: Account for message limits
        List<LowLevelComponent> buttons = new ArrayList<>();
        for (TrackingChannelNotificationAction action : trackingChannels) {
            embed.addField("Channel name:", action.channelName());
            if (buttons.size() >= 5) {
                continue;
            }

            buttons.add(Button.create(action.id().raw(),
                                      ButtonStyle.PRIMARY,
                                      "Link %s".formatted(action.channelName())));
        }

        return new MessageBuilder().addEmbed(embed).addComponents(ActionRow.of(buttons));
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
