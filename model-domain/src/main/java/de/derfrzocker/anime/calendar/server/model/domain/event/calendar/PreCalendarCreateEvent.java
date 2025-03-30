package de.derfrzocker.anime.calendar.server.model.domain.event.calendar;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;

public record PreCalendarCreateEvent(CalendarCreateData createData, Calendar calendar, RequestContext context) {

}
