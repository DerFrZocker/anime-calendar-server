package de.derfrzocker.anime.calendar;

import de.derfrzocker.anime.calendar.actor.ActorId;

import java.time.Instant;

public record RequestContext(ActorId requestActor, Instant requestTime) {

}
