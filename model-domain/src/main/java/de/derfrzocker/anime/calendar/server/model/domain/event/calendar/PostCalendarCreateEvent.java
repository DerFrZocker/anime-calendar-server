package de.derfrzocker.anime.calendar.server.model.domain.event.calendar;

import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;

public record PostCalendarCreateEvent(CalendarCreateData createData, Calendar calendar, RequestContext context) {

}
