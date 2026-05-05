package de.derfrzocker.anime.calendar.core.user;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;

public record UserId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'U';

    public UserId {
        validate(raw, () -> new InvalidValueFormatException("UserId is invalid."));
    }

    public static UserId of(String raw) {
        return new UserId(raw);
    }

    public static <E extends Throwable> UserId of(String raw, Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new UserId(raw);
    }

    private static <E extends Throwable> void validate(String raw, Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw throwable.get();
        }
    }
}
