package de.derfrzocker.anime.calendar.server.rest.transfer.calendar;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;

public record CalendarAnimeLinkTO(CalendarId calendarId, AnimeId animeId, boolean include) {

}
