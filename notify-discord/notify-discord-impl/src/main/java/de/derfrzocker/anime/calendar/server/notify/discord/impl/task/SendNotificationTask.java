package de.derfrzocker.anime.calendar.server.notify.discord.impl.task;

import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.config.DiscordConfig;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationSendEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

@ApplicationScoped
public class SendNotificationTask {

    @Inject
    Instance<DiscordMessageRenderer> rendererInstance;
    @Inject
    JDA jda;
    @Inject
    DiscordConfig config;

    public void onNotificationSend(@Observes NotificationSendEvent event) {
        DiscordMessageRenderer renderer = selectRenderer(event.notification().type());

        MessageCreateBuilder builder = renderer.render(new NotificationHolder(event.notification(), event.actions()),
                                                       event.context());

        this.jda.getTextChannelById(this.config.getChannelId()).sendMessage(builder.build()).queue();
    }

    private DiscordMessageRenderer selectRenderer(NotificationType type) {
        return this.rendererInstance.select(NamedLiteral.of(type.raw() + DiscordMessageRenderer.NAME_SUFFIX)).get();
    }
}
