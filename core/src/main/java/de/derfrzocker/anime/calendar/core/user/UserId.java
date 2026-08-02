package de.derfrzocker.anime.calendar.core.user;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;

import java.util.Objects;

public record UserId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'U';

    public UserId {
        validate(raw);
    }

    public static UserId of(String raw) {
        return new UserId(raw);
    }

    private static void validate(String raw) {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw new InvalidValueFormatException("UserId is invalid.");
        }
    }
}
