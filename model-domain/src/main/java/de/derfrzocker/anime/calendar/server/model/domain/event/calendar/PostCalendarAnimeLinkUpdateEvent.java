package de.derfrzocker.anime.calendar.server.model.domain.event.calendar;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLink;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarAnimeLinkUpdateData;

public record PostCalendarAnimeLinkUpdateEvent(CalendarId calendarId, AnimeId animeId,
                                               CalendarAnimeLinkUpdateData updateData, CalendarAnimeLink current,
                                               CalendarAnimeLink updated, RequestContext context) {

}
