package de.derfrzocker.anime.calendar.server.notify.discord.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;

public interface DiscordInputApplier {

    void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context);
}
