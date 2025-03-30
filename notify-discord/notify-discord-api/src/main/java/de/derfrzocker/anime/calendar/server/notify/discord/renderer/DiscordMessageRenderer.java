package de.derfrzocker.anime.calendar.server.notify.discord.renderer;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationHolder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public interface DiscordMessageRenderer {

    String NAME_SUFFIX = "-discord-message-renderer";

    MessageCreateBuilder render(NotificationHolder holder, RequestContext context);
}
