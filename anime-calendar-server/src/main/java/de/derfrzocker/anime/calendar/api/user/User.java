package de.derfrzocker.anime.calendar.api.user;

import de.derfrzocker.anime.calendar.server.model.core.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.UserId;
import java.time.Instant;
import java.util.Set;

public record User(UserId userId, Instant createdAt, Set<CalendarId> calendars, Set<AnimeAccountLinkId> animeAccountLinks) {
}
