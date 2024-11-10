package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

public record Calendar(CalendarId id, CalendarKey key, Instant createdAt, UserId owner,
                       Set<AnimeAccountLinkId> animeAccountLinks, Map<AnimeOverrideID, AnimeOverride> animeOverrides) {

}
