package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TrackingChannelNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.TrackingChannelNotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@ApplicationScoped
@Named("TrackingChannel" + DiscordMessageRenderer.NAME_SUFFIX)
public class TrackingChannelDiscordMessageRenderer implements DiscordMessageRenderer {

    @Inject
    TrackingChannelNotificationActionService trackingActionService;

    @Override
    public MessageCreateBuilder render(NotificationHolder holder, RequestContext context) {
        List<TrackingChannelNotificationAction> trackingChannels = toSpecificAction(holder.actions(), context);
        EmbedBuilder embed = new EmbedBuilder();

        if (trackingChannels.isEmpty()) {
            // TODO 2025-02-23: Log better error here
            return new MessageCreateBuilder().addEmbeds(new EmbedBuilder().setTitle(
                    "ERROR [TrackingChannel]: Nothing found").build());
        }

        TID tid = trackingChannels.getFirst().tid();
        String title = trackingChannels.getFirst().title();
        embed.setTitle("[%s] %s".formatted(tid.raw(), title))
             .setDescription("Links: %s\nFound following channels:".formatted(getUrl(tid)));

        // TODO 2024-12-23: Account for message limits
        List<ItemComponent> buttons = new ArrayList<>();
        for (TrackingChannelNotificationAction action : trackingChannels) {
            embed.addField("Channel name:", action.channelName(), false);
            if (buttons.size() >= 5) {
                continue;
            }

            buttons.add(Button.of(ButtonStyle.PRIMARY, action.id().raw(), "Link %s".formatted(action.channelName())));
        }

        return new MessageCreateBuilder().addEmbeds(embed.build()).addComponents(ActionRow.of(buttons));
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
