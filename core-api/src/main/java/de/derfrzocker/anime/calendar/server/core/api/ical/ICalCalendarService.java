package de.derfrzocker.anime.calendar.server.core.api.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeOptions;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import java.util.Set;

public interface ICalCalendarService {

    ICalCalendar build(Set<AnimeId> ids, AnimeOptions options, RequestContext context);
}
