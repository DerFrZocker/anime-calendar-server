package de.derfrzocker.anime.calendar.server.model.core;

public record AnimeId(String raw) implements Id {

    @Override
    public IdType idType() {
        return IdType.ANIME;
    }
}
