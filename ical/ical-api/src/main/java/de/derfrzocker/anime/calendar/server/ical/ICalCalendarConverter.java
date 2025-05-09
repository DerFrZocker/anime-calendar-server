package de.derfrzocker.anime.calendar.server.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.episode.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.ical.api.ICalCalendar;
import java.util.Collection;

public interface ICalCalendarConverter {

    ICalCalendar convert(Collection<AnimeEpisodes> animeEpisodes, RequestContext context);
}
