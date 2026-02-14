package de.derfrzocker.anime.calendar.server.notify.discord.impl.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "notify.discord")
public interface DiscordConfig {

    @WithName("channel-id")
    String channelId();
}
