package de.derfrzocker.anime.calendar.server.impl.notify.discord.config;

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
