package de.derfrzocker.anime.calendar.notify;

import java.util.Objects;
import java.util.UUID;

public record NotifyId(UUID raw) {

    public static NotifyId of(UUID raw) {
        return new NotifyId(raw);
    }

    public NotifyId {
        Objects.requireNonNull(raw);
    }
}
