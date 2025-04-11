package de.derfrzocker.anime.calendar.core.notify;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public record NotificationActionId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'O';

    public NotificationActionId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
