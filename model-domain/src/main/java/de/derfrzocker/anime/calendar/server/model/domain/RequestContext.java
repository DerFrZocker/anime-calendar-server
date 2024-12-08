package de.derfrzocker.anime.calendar.server.model.domain;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.time.Instant;

public record RequestContext(UserId requestUser, Instant requestTime) {

}
