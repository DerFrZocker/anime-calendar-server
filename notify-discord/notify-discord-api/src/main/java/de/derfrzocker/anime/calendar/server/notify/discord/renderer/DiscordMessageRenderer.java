package de.derfrzocker.anime.calendar.server.notify.discord.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import org.javacord.api.entity.message.MessageBuilder;

public interface DiscordMessageRenderer {

    String NAME_SUFFIX = "-discord-message-renderer";

    MessageBuilder render(NotificationHolder holder, RequestContext context);
}
