package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.UserId;
import java.time.Instant;
import java.util.Set;

public record Calendar(CalendarId calendarId, CalendarKey calendarKey, Instant createdAt, UserId ownerId, Set<AnimeAccountLinkId> animeAccountLinks, Set<AnimeOverride> animeOverrides) {
}
