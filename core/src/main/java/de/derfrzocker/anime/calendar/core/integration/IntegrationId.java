package de.derfrzocker.anime.calendar.core.integration;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;

import java.util.Objects;

public record IntegrationId(String raw) {

    public static final int ID_MIN_LENGTH = 5;
    public static final int ID_MAX_LENGTH = 11;

    public IntegrationId {
        validate(raw);
    }

    public static IntegrationId of(String raw) {
        return new IntegrationId(raw);
    }

    private static void validate(String raw) {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isLengthBetween(raw, ID_MIN_LENGTH, ID_MAX_LENGTH)) {
            throw new InvalidValueFormatException("IntegrationId '%s' is invalid.".formatted(raw));
        }

        if (!ValidatorUtil.hasOnlyValidChars(raw, ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE)) {
            throw new InvalidValueFormatException("IntegrationId '%s' is invalid.".formatted(raw));
        }
    }
}
