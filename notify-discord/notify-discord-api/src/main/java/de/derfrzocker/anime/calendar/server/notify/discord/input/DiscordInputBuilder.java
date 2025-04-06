package de.derfrzocker.anime.calendar.server.notify.discord.input;

public interface DiscordInputBuilder {

    DiscordInputBuilder setTitle(String title);

    DiscordInputBuilder addTextField(String id, String label, int minLength, int maxLength);
}
