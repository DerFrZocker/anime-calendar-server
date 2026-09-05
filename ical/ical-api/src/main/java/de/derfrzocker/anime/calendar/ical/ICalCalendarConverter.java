package de.derfrzocker.anime.calendar.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.episode.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.episode.api.AnimeOptions;
import de.derfrzocker.anime.calendar.ical.api.ICalCalendar;
import java.util.Collection;

public interface ICalCalendarConverter {

    ICalCalendar convert(Collection<AnimeEpisodes> animeEpisodes, AnimeOptions options, RequestContext context);
}
