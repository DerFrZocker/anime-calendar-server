package de.derfrzocker.anime.calendar.core.notify;

import java.util.Objects;

public record NotificationId(String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'N';

    public NotificationId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
