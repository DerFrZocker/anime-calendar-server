package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import java.util.Set;

public record CalendarTO(CalendarId id, CalendarKey key, Set<AnimeOverrideTO> animeOverrides) {

}
