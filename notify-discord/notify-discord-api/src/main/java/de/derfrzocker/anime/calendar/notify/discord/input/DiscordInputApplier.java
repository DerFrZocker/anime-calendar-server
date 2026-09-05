package de.derfrzocker.anime.calendar.notify.discord.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.notify.api.NotificationAction;

public interface DiscordInputApplier {

    void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context);
}
