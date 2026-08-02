package de.derfrzocker.anime.calendar.core.anime;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;

import java.util.Objects;

public record AnimeId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'A';

    public AnimeId {
        validate(raw);
    }

    public static AnimeId of(String raw) {
        return new AnimeId(raw);
    }

    private static void validate(String raw) {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw new InvalidValueFormatException("AnimeId '%s' is invalid.".formatted(raw));
        }
    }
}
