package de.derfrzocker.anime.calendar.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.ical.api.ICalCalendar;
import java.util.Collection;

public interface ICalCalendarBuilder {

    ICalCalendar build(Collection<AnimeId> ids, AnimeOptions options, RequestContext context);
}
