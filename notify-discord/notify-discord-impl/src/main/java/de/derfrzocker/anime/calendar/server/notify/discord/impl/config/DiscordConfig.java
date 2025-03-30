package de.derfrzocker.anime.calendar.server.notify.discord.impl.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DiscordConfig {

    @Inject
    @ConfigProperty(name = "discord.bot.channel-id")
    String channelId;

    public String getChannelId() {
        return this.channelId;
    }
}
