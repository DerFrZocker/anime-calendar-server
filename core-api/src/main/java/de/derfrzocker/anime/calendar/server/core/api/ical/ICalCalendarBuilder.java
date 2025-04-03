package de.derfrzocker.anime.calendar.server.core.api.ical;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.anime.api.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import java.util.List;

public interface ICalCalendarBuilder {

    ICalCalendar build(List<AnimeEpisodes> episodes, RequestContext context);
}
