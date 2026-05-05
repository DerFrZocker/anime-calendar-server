package de.derfrzocker.anime.calendar.core.integration;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;

public record IntegrationId(String raw) {

    public static final int ID_MIN_LENGTH = 5;
    public static final int ID_MAX_LENGTH = 11;

    public IntegrationId {
        validate(raw, () -> new InvalidValueFormatException("IntegrationId '%s' is invalid.".formatted(raw)));
    }

    public static IntegrationId of(String raw) {
        return new IntegrationId(raw);
    }

    public static <E extends Throwable> IntegrationId of(String raw, Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new IntegrationId(raw);
    }

    private static <E extends Throwable> void validate(String raw, Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isLengthBetween(raw, ID_MIN_LENGTH, ID_MAX_LENGTH)) {
            throw throwable.get();
        }

        if (!ValidatorUtil.hasOnlyValidChars(raw, ValidatorUtil.ValidChars.A_TO_Z_LOWERCASE)) {
            throw throwable.get();
        }
    }
}
