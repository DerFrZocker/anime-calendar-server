package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.core.util.Change;
import java.time.Instant;

public record NotificationActionUpdateData(Change<Instant> executedAt, Change<UserId> executedBy) {

}
