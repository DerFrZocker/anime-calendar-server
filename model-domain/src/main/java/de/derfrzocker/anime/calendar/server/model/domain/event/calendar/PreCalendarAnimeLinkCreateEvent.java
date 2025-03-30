package de.derfrzocker.anime.calendar.server.model.domain.event.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkCreateData;

public record PreCalendarAnimeLinkCreateEvent(CalendarId calendarId, AnimeId animeId,
                                              CalendarAnimeLinkCreateData createData, CalendarAnimeLink link,
                                              RequestContext context) {

}
