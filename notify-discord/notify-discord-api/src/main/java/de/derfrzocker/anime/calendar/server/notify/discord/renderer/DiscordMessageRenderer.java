package de.derfrzocker.anime.calendar.server.notify.discord.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;

public interface DiscordMessageRenderer {

    void render(NotificationHolder holder, DiscordMessageBuilder builder, RequestContext context);
}
