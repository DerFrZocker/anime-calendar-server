package de.derfrzocker.anime.calendar.server.notify.discord.renderer;

public interface DiscordMessageBuilder {

    DiscordMessageBuilder setTitle(String title);

    DiscordMessageBuilder setDescription(String description);

    DiscordMessageBuilder addField(String title, String description);

    DiscordMessageBuilder addButton(String title, String id);
}
