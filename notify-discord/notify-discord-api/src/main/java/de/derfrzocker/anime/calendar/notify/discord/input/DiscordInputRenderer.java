package de.derfrzocker.anime.calendar.notify.discord.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;

public interface DiscordInputRenderer {

    void render(NotificationAction action, DiscordInputBuilder builder, RequestContext context);
}
