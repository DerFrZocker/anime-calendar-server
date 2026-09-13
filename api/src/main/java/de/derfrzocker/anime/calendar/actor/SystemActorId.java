package de.derfrzocker.anime.calendar.actor;

import java.util.Objects;

public record SystemActorId(String raw) implements ActorId {

    public static SystemActorId of(String raw) {
        return new SystemActorId(raw);
    }

    public SystemActorId {
        Objects.requireNonNull(raw);
    }
}
