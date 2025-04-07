package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputBuilder;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputRenderer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("NewAnime" + DiscordInputRenderer.NAME_SUFFIX)
public class NewAnimeDiscordInputRenderer implements DiscordInputRenderer {

    @Override
    public void render(NotificationAction action, DiscordInputBuilder builder, RequestContext context) {
        builder.setTitle("Manual Set Title");

        builder.addTextField("title", "title", 1, 2048);
    }
}
