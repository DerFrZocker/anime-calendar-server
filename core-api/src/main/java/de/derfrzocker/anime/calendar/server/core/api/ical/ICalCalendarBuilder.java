package de.derfrzocker.anime.calendar.server.core.api.ical;

import de.derfrzocker.anime.calendar.server.model.domain.ical.AnimeEpisodes;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.ical.ICalCalendar;
import java.util.List;

public interface ICalCalendarBuilder {

    ICalCalendar build(List<AnimeEpisodes> episodes, RequestContext context);
}
