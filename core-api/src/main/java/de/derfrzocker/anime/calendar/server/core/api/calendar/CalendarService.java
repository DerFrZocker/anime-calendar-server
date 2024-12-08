package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import java.util.Optional;

public interface CalendarService {

    Optional<Calendar> getById(CalendarId id, RequestContext context);

    Calendar createWithData(CalendarCreateData createData, RequestContext context);
}
