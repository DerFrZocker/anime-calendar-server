package de.derfrzocker.anime.calendar.server.model.core.exception;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;

public class InvalidValueException extends RuntimeException {

    private static final String INVALID_VALUE = " '%s' for value '%s' is not valid.";
    private static final String ANIME_ID_VALUE = "AnimeId" + INVALID_VALUE;
    private static final String STRING_VALUE = "String" + INVALID_VALUE;

    public static InvalidValueException with(String key, AnimeId value) {
        if (value == null) {
            return with(key, "<null>", ANIME_ID_VALUE);
        }

        return with(key, value.raw(), ANIME_ID_VALUE);
    }

    public static InvalidValueException with(String key, String value) {
        if (value == null) {
            return with(key, "<null>", STRING_VALUE);
        }

        return with(key, value, STRING_VALUE);
    }

    private static InvalidValueException with(String key, String value, String message) {
        return new InvalidValueException(message.formatted(key, value));
    }

    private InvalidValueException(String message) {
        super(message);
    }
}
