package de.derfrzocker.anime.calendar.api.user;

import de.derfrzocker.anime.calendar.api.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.api.calendar.CalendarId;
import java.time.Instant;
import java.util.Set;

public record User(UserId userId, Instant createdAt, Set<CalendarId> calendars, Set<AnimeAccountLinkId> animeAccountLinks) {
}
