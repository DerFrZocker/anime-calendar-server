package de.derfrzocker.anime.calendar.core.calendar;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public record CalendarId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'C';

    public CalendarId {
        validate(raw, () -> new InvalidValueFormatException("CalendarId '%s' is invalid.".formatted(raw)));
    }

    @NotNull
    public static CalendarId of(@NotNull String raw) {
        return new CalendarId(raw);
    }

    @NotNull
    public static <E extends Throwable> CalendarId of(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new CalendarId(raw);
    }

    private static <E extends Throwable> void validate(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw throwable.get();
        }
    }
}
