package de.derfrzocker.anime.calendar.actor;

import java.util.Objects;
import java.util.UUID;

public record ActorId(UUID raw) {

    public static ActorId of(UUID raw) {
        return new ActorId(raw);
    }

    public ActorId {
        Objects.requireNonNull(raw);
    }
}
