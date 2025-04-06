package de.derfrzocker.anime.calendar.server.notify.discord.input;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;

public interface DiscordInputApplier {

    String NAME_SUFFIX = "-discord-input-applier";

    void apply(NotificationAction action, DiscordInputValuesProvider provider, RequestContext context);
}
