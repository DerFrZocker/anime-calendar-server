package de.derfrzocker.anime.calendar.server.rest.response.calendar;

import de.derfrzocker.anime.calendar.server.rest.transfer.calendar.CalendarAnimeLinkTO;
import java.util.List;

public record CalendarAnimeLinkListResponse(List<CalendarAnimeLinkTO> calendarAnimeLinks) {

}
