package de.derfrzocker.anime.calendar.api.calendar;

import de.derfrzocker.anime.calendar.api.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.api.user.UserId;
import java.time.Instant;
import java.util.Set;

public record Calendar(CalendarId calendarId, CalendarKey calendarKey, Instant createdAt, UserId ownerId, Set<AnimeAccountLinkId> animeAccountLinks, Set<AnimeOverride> animeOverrides) {
}
