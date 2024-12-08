package de.derfrzocker.anime.calendar.server.core.impl.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarDao;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CalendarServiceImpl implements CalendarService {

    @Inject
    CalendarDao dao;
    @Inject
    CalendarEventPublisher eventPublisher;

    @Override
    public Optional<Calendar> getById(CalendarId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public Calendar createWithData(CalendarCreateData createData, RequestContext context) {
        CalendarId id = createNewCalendarId(context);
        CalendarKey key = StringGenerator.generateCalendarKey(id);

        Calendar calendar = Calendar.from(id, key, createData, context);

        this.eventPublisher.firePreCreateEvent(createData, calendar, context);
        this.dao.create(calendar, context);
        this.eventPublisher.firePostCreateEvent(createData, calendar, context);

        return calendar;
    }

    private CalendarId createNewCalendarId(RequestContext context) {
        CalendarId calendarId;
        do {
            calendarId = StringGenerator.generateCalendarId();
        } while (getById(calendarId, context).isPresent());

        return calendarId;
    }
}
