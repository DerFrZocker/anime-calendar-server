package de.derfrzocker.anime.calendar.server.model.core.calendar;

public record AnimeOverrideID(String raw) {

    public static final int ANIME_OVERRIDE_KEY_LENGTH = 10;
    public static final char ANIME_OVERRIDE_PREFIX_CHAR = 'V';
}
