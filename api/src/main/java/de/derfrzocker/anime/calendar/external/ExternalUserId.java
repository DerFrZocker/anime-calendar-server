package de.derfrzocker.anime.calendar.external;

import java.util.Objects;

public record ExternalUserId(String raw) {

    public static ExternalUserId of(String raw) {
        return new ExternalUserId(raw);
    }

    public ExternalUserId {
        Objects.requireNonNull(raw);
    }
}
