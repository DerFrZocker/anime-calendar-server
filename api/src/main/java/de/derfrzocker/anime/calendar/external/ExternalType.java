package de.derfrzocker.anime.calendar.external;

import java.util.Objects;

public record ExternalType(String raw) {

    public static ExternalType of(String raw) {
        return new ExternalType(raw);
    }

    public ExternalType {
        Objects.requireNonNull(raw);
    }
}
