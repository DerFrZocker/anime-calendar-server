package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

public record ResolvedChannel(ChannelId id, String name) {

    public static ResolvedChannel from(Channel channel) {
        return new ResolvedChannel(channel.id(), channel.name());
    }
}
