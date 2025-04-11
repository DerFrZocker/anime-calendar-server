package de.derfrzocker.anime.calendar.core.calendar;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record CalendarId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'C';

    public CalendarId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
