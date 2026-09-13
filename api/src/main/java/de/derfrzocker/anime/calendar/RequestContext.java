package de.derfrzocker.anime.calendar;

import de.derfrzocker.anime.calendar.actor.ActorId;

import java.time.Instant;
import java.util.Objects;

public record RequestContext(ActorId requestActor, Instant requestTime) {

    public RequestContext {
        Objects.requireNonNull(requestActor);
        Objects.requireNonNull(requestTime);
    }
}
