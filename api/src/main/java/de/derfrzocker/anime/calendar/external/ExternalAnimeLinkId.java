package de.derfrzocker.anime.calendar.external;

import java.util.Objects;
import java.util.UUID;

public record ExternalAnimeLinkId(UUID raw) {

    public static ExternalAnimeLinkId of(UUID raw) {
        return new ExternalAnimeLinkId(raw);
    }

    public ExternalAnimeLinkId {
        Objects.requireNonNull(raw);
    }
}
