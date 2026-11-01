package de.derfrzocker.anime.calendar.notify;

import java.util.Objects;
import java.util.UUID;

public record NotifyActionId(UUID raw) {

    public static NotifyActionId of(UUID raw) {
        return new NotifyActionId(raw);
    }

    public NotifyActionId {
        Objects.requireNonNull(raw);
    }
}
