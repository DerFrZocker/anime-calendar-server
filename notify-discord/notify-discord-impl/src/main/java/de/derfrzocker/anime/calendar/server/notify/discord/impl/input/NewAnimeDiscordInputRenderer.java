package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputRenderer;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Identifier(NewAnimeNotificationAction.NOTIFICATION_ACTION_TYPE_RAW)
public class NewAnimeDiscordInputRenderer implements DiscordInputRenderer {

    @Override
    public void render(NotificationAction action, DiscordInputBuilder builder, RequestContext context) {
        builder.setTitle("Manual Set Title");

        builder.addTextField("title", "Title", 1, 2048);
    }
}
