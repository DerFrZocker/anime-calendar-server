package de.derfrzocker.anime.calendar.core;

import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public record RequestContext(UserId requestUser, Instant requestTime) {

}
