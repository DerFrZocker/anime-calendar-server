package de.derfrzocker.anime.calendar.server.core.api.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import java.util.Optional;

public interface CalendarDao {

    Optional<Calendar> getById(CalendarId id);

    Calendar createWithData(CalendarCreateData calendarCreateData);

    Calendar updateWithChangeData(Calendar calendar, CalendarChangeData calendarChangeData);
}
