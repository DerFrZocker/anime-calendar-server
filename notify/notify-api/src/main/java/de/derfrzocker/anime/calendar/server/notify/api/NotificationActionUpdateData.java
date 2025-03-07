package de.derfrzocker.anime.calendar.server.notify.api;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import java.time.Instant;

public record NotificationActionUpdateData(Change<Instant> executedAt, Change<UserId> executedBy) {

}
