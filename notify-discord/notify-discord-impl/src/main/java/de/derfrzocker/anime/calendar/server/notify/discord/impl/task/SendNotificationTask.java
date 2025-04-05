package de.derfrzocker.anime.calendar.server.notify.discord.impl.task;

import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationType;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.config.DiscordConfig;
import de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer.JDADiscordMessageBuilderImpl;
import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageRenderer;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationSendEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SendNotificationTask {

    private static final Logger LOG = Logger.getLogger(SendNotificationTask.class);

    @Inject
    Instance<DiscordMessageRenderer> rendererInstance;
    @Inject
    JDA jda;
    @Inject
    DiscordConfig config;

    public void onNotificationSend(@Observes NotificationSendEvent event) {
        DiscordMessageRenderer renderer = selectRenderer(event.notification().type());

        try {
            TextChannel channel = this.jda.getTextChannelById(this.config.getChannelId());
            JDADiscordMessageBuilderImpl builder = new JDADiscordMessageBuilderImpl();

            renderer.render(new NotificationHolder(event.notification(), event.actions()), builder, event.context());

            builder.build().forEach(message -> channel.sendMessage(message).queue());
        } catch (Exception e) {
            LOG.errorv(e, "Failed to send notification, for event '{}'.", event);
            trySendException(e);
        }
    }

    private DiscordMessageRenderer selectRenderer(NotificationType type) {
        return this.rendererInstance.select(NamedLiteral.of(type.raw() + DiscordMessageRenderer.NAME_SUFFIX)).get();
    }

    private void trySendException(Exception exception) {
        try {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Failed to send notification");
            embed.setDescription(exception.getMessage());

            MessageCreateBuilder builder = new MessageCreateBuilder();
            builder.setEmbeds(embed.build());

            this.jda.getTextChannelById(this.config.getChannelId()).sendMessage(builder.build()).queue();
        } catch (Exception e) {
            LOG.errorv("Could not send error message to Discord.", e);
        }
    }
}
