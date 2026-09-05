package de.derfrzocker.anime.calendar.notify.discord.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.notify.api.NotificationHolder;

public interface DiscordMessageActionRenderer {

    void render(
            NotificationHolder holder,
            NotificationAction action,
            DiscordMessageBuilder builder,
            RequestContext context);
}
