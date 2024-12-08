package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import java.util.Optional;

public interface CalendarDao {

    Optional<Calendar> getById(CalendarId calendarId, RequestContext context);

    void create(Calendar calendar, RequestContext context);

    void update(Calendar calendar, RequestContext context);

    void delete(Calendar calendar, RequestContext context);
}
