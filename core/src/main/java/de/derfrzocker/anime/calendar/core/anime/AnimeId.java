package de.derfrzocker.anime.calendar.core.anime;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;

public record AnimeId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'A';

    public AnimeId {
        validate(raw, () -> new InvalidValueFormatException("AnimeId '%s' is invalid.".formatted(raw)));
    }

    public static AnimeId of(String raw) {
        return new AnimeId(raw);
    }

    public static <E extends Throwable> AnimeId of(String raw, Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new AnimeId(raw);
    }

    private static <E extends Throwable> void validate(String raw, Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw throwable.get();
        }
    }
}
