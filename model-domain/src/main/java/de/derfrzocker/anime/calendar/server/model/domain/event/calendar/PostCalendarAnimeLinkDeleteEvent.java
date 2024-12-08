package de.derfrzocker.anime.calendar.server.model.domain.event.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;

public record PostCalendarAnimeLinkDeleteEvent(CalendarId calendarId, AnimeId animeId, CalendarAnimeLink link,
                                               RequestContext context) {

}
