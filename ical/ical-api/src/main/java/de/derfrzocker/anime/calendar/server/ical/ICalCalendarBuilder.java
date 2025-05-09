package de.derfrzocker.anime.calendar.server.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.server.ical.api.ICalCalendar;
import java.util.Collection;

public interface ICalCalendarBuilder {

    ICalCalendar build(Collection<AnimeId> ids, AnimeOptions options, RequestContext context);
}
