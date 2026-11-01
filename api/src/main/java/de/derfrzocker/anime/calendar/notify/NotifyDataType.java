package de.derfrzocker.anime.calendar.notify;

import java.util.Objects;

public record NotifyDataType(String raw) {

    public static NotifyDataType of(String raw) {
        return new NotifyDataType(raw);
    }

    public NotifyDataType {
        Objects.requireNonNull(raw);
    }
}
