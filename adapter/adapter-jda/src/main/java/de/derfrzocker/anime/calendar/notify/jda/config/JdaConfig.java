package de.derfrzocker.anime.calendar.notify.jda.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "adapter.jda")
public interface JdaConfig {

    @WithName("token")
    String token();

    @WithName("channel-id")
    String channelId();
}
