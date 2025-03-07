package de.derfrzocker.anime.calendar.server.impl.notify.discord.api;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import org.javacord.api.entity.message.MessageBuilder;

public interface DiscordMessageRenderer {

    String NAME_SUFFIX = "-discord-message-renderer";

    MessageBuilder render(NotificationHolder holder, RequestContext context);
}
