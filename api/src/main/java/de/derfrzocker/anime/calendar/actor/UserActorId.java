package de.derfrzocker.anime.calendar.actor;

import java.util.Objects;

public record UserActorId(String raw) implements ActorId {

    public static UserActorId of(String raw) {
        return new UserActorId(raw);
    }

    public UserActorId {
        Objects.requireNonNull(raw);
    }
}
