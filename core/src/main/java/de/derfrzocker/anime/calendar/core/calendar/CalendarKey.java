package de.derfrzocker.anime.calendar.core.calendar;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record CalendarKey(@NotNull String raw) {

    public static final int KEY_LENGTH = CalendarId.ID_LENGTH + 30;
    public static final char KEY_PREFIX_CHAR = 'K';

    @NotNull
    public CalendarId calendarId() {
        return new CalendarId(raw().substring(0, CalendarId.ID_LENGTH));
    }

    public CalendarKey {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
