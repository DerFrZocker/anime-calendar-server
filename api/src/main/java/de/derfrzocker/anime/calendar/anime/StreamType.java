package de.derfrzocker.anime.calendar.anime;

import java.util.Objects;

public record StreamType(String raw) {

    public StreamType {
        Objects.requireNonNull(raw);
    }
}
