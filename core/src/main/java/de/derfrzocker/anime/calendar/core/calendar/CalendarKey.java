package de.derfrzocker.anime.calendar.core.calendar;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public record CalendarKey(@NotNull String raw) {

    public static final int KEY_LENGTH = CalendarId.ID_LENGTH + 30;
    public static final char KEY_PREFIX_CHAR = 'K';

    @NotNull
    public CalendarId calendarId() {
        return CalendarId.of(raw().substring(0, CalendarId.ID_LENGTH));
    }

    public CalendarKey {
        validate(raw, () -> new InvalidValueFormatException("CalendarKey is invalid."));
    }

    @NotNull
    public static CalendarKey of(@NotNull String raw) {
        return new CalendarKey(raw);
    }

    @NotNull
    public static <E extends Throwable> CalendarKey of(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new CalendarKey(raw);
    }

    private static <E extends Throwable> void validate(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidLength(raw, KEY_LENGTH)) {
            throw throwable.get();
        }

        if (!ValidatorUtil.isValidChar(raw, KEY_PREFIX_CHAR, CalendarId.ID_LENGTH)) {
            throw throwable.get();
        }

        if (!ValidatorUtil.isValidStartChar(raw, CalendarId.ID_PREFIX)) {
            throw throwable.get();
        }

        if (!ValidatorUtil.hasOnlyValidChars(
                raw,
                ValidatorUtil.ValidChars.A_TO_Z_UPPERCASE,
                ValidatorUtil.ValidChars.DIGITS,
                ValidatorUtil.ValidChars.DISALLOW_ZERO,
                ValidatorUtil.ValidChars.DISALLOW_O)) {
            throw throwable.get();
        }
    }
}
