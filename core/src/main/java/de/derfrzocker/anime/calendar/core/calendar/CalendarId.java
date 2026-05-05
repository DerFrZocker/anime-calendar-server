package de.derfrzocker.anime.calendar.core.calendar;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;

public record CalendarId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'C';

    public CalendarId {
        validate(raw, () -> new InvalidValueFormatException("CalendarId '%s' is invalid.".formatted(raw)));
    }

    public static CalendarId of(String raw) {
        return new CalendarId(raw);
    }

    public static <E extends Throwable> CalendarId of(String raw, Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new CalendarId(raw);
    }

    private static <E extends Throwable> void validate(String raw, Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw throwable.get();
        }
    }
}
