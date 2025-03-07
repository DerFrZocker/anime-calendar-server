package de.derfrzocker.anime.calendar.server.impl.notify.discord.task;

import de.derfrzocker.anime.calendar.server.impl.notify.discord.api.DiscordMessageRenderer;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationSendEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.MessageBuilder;

@ApplicationScoped
public class SendNotificationTask {

    @Inject
    Instance<DiscordMessageRenderer> rendererInstance;
    @Inject
    DiscordApi api;
    @ConfigProperty(name = "discord.bot.name-link.channel-id")
    String channelId;

    public void onNotificationSend(@Observes NotificationSendEvent event) {
        DiscordMessageRenderer renderer = selectRenderer(event.notification().type());

        MessageBuilder builder = renderer.render(new NotificationHolder(event.notification(), event.actions()),
                                                 event.context());

        builder.send(this.api.getChannelById(this.channelId).flatMap(Channel::asTextChannel).orElseThrow());
    }

    private DiscordMessageRenderer selectRenderer(NotificationType type) {
        return this.rendererInstance.select(NamedLiteral.of(type.raw())).get();
    }
}
